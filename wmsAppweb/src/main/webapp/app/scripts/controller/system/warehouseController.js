
define(['scripts/controller/controller', '../../model/system/warehouseModel'], function (controller, warehouseModel) {
    "use strict";
    controller.controller('warehouseController', ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
            function ($scope, $rootScope, $sync, $url, wmsDataSource) {
                var warehouseUrl = $url.systemWarehouseUrl,
                    warehouseColumns = [
                        WMS.GRIDUTILS.CommonOptionButton(),
                        { filterable: false, title: '仓库编号', field: 'warehouseNo', align: 'left', width: "120px"},
                        { filterable: false, title: '仓库名称', field: 'warehouseName', align: 'left', width: "120px"},
                        { filterable: false, title: '仓库类型', field: 'typeCode', align: 'left', width: "120px" ,template: WMS.UTILS.codeFormat('typeCode', 'WarehouseType')},
                        { filterable: false, title: '是否可用', field: 'isActive', align: 'left', width: "150px", template: WMS.UTILS.checkboxDisabledTmp("isActive")}

                    ],
                    userWarehouseColumns = [
                        WMS.GRIDUTILS.deleteOptionButton("detail"),
                        { filterable: false, title: '登入账号', field: 'userName', align: 'left', width: "50px"},
                        { filterable: false, title: '用户名称', field: 'realName', align: 'left', width: "50px"}
                    ],
                    allocatAbleUserColumns = [
                        { filterable: false, title: '登入账号', field: 'userName', align: 'left', width: "80px"},
                        { filterable: false, title: '用户名称', field: 'realName', align: 'left', width: "80px"}
                    ],

                    warehouseDataSource = wmsDataSource({
                        url: warehouseUrl,
                        schema: {
                            model: warehouseModel.header
                        }
                    });

                allocatAbleUserColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

                warehouseColumns = warehouseColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
                userWarehouseColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

                $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                    dataSource: warehouseDataSource,
                    toolbar: [
                        { name: "create", text: "新增", className: "btn-auth-add"}
                    ],
                    columns: warehouseColumns,
                    editable: {
                        mode: "popup",
                        template: kendo.template($("#warehouseEditor").html()),
                        window: {
                            width: "640px"
                        }
                    },
                    filterable: true
                }, $scope);
                $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;
                $scope.selectAllRow = WMS.GRIDUTILS.selectAllRow;

                $scope.detailOptions = function(dataItem) {
                    var detailOptions = WMS.GRIDUTILS.getGridOptions({
                        dataSource: wmsDataSource({
                            url: warehouseUrl + "/" + dataItem.id+"/user",
                            schema: {
                                model: {
                                    id: "id",
                                    fields: {
                                        id: {type: "number", editable: false, nullable: true }
                                    }
                                }
                            }
                        }),
                        editable: {
                            mode: "popup"
                        },
                        toolbar: [
                            { template:'<a class="k-button k-button-custom-command" ng-click="permission(dataItem)">分配用户</a>', className: "btn-auth-addWhUser"},
                            { template:'<a class="k-button k-button-custom-command" ng-click="batchDelete(this)">批量删除</a>', className: "btn-auth-batchDelete"}
                        ],
                        columns: userWarehouseColumns
                    }, $scope);
                    return detailOptions;
                };

                $scope.permission = function(dataItem){
                    $scope.permissionPopup.refresh().open().center();
                    $scope.gridUUid = this.warehouseDetailGrid;
                    var userDataSource = wmsDataSource({
                        serverPaging:false,
                        url: warehouseUrl+"/"+ dataItem.id +"/allocatable/user",
                        schema: {
                            model: {
                                id: "id",
                                fields: {
                                    id: {type: "number", editable: false, nullable: true }
                                }
                            }
                        }
                    });
                    this.userGrid.setDataSource(userDataSource);
                    this.userGrid.refresh();
                    $scope.warehouseId = dataItem.id;
                };

                $scope.userGridOptions = WMS.GRIDUTILS.getGridOptions({
                    dataSource: {},
                    pageable: false,
                    editable: false,
                    toolbar: [
                        { template:'<a class="k-button k-button-custom-command" ng-click="saveUser()">保存分配</a>'}
                    ],
                    columns: allocatAbleUserColumns,
                    filterable: true
                });

                //保存分配用户
                $scope.saveUser = function (){
                    var ids ="";
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.userGrid);
                    for(var i=0;i<selectedData.length;i++){
                        ids += selectedData[i].id+",";
                    }
                    ids = ids.substring(0,ids.length-1);
                    if(ids === ""){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    var warehouseId = $scope.warehouseId;
                    var params={warehouseId:warehouseId,userIds:ids};
                    $sync(warehouseUrl+"/"+warehouseId+"/allocatable/user","POST",{data: params}).
                       then(function(){
                          $scope.permissionPopup.close();
                          $scope.gridUUid.dataSource.read();
                       }) ;
                };

                //批量删除分配用户
                $scope.batchDelete = function(e){
                    var ids = "";
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData(e.warehouseDetailGrid);
                    for(var i=0;i<selectedData.length;i++){
                        ids+=selectedData[i].id+",";
                    }
                    ids = ids.substring(0,ids.length-1);
                    var warehouseId = e.dataItem.id;
                    if(ids ===""){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    $sync(warehouseUrl +"/"+warehouseId+ "/allocated/user/" + ids, "DELETE")
                        .then(function (xhr) {
                            e.warehouseDetailGrid.dataSource.read({});
                        }, function (xhr) {
                            e.warehouseDetailGrid.dataSource.read({});
                        });
                };
            }
        ]
    );
});
