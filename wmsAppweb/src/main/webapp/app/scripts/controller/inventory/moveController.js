define(['../controller', '../../model/inwh/moveModel'], function (controller, moveModel) {
    "use strict";
    controller.controller('moveController',['$scope', '$rootScope', 'sync', 'wmsDataSource', 'wmsLog', '$filter', 'url',
        function ($scope, $rootScope, $sync, wmsDataSource, wmsLog, $filter, $url) {

            var moveUrl = $url.inventoryMoveUrl,

                moveColumns = [
                 WMS.GRIDUTILS.CommonOptionButton(),
                    {title: ' 移库单号', field: 'id', align: 'left', width: "100px"},
                    { title: ' 货主', field: 'cargoOwnerId', align: 'left', width: "100px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: ' 移货原因', field: 'moveReason', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('moveReason','MoveReason')},
                    { title: ' 数据来源', field: 'datasourceCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('datasourceCode','DataSource')},
                    { title: ' 单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: ' 商品编码', field: 'sku', align: 'left', width: "140px"},
                    { title: ' 商品名称', field: 'skuName', align: 'left', width: "140px"},
                    { title: ' 商品条码', field: 'barcode', align: 'left', width: "140px"},
                    { title: ' 来源库房', field: 'fromRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("fromRoomId")},
                    { title: ' 目的库房', field: 'toRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("toRoomId")},
                    { title: ' 移货数量', field: 'movedQty', align: 'left', width: "100px"},
                    { title: ' 移货描述', field: 'description', align: 'left', width: "100px"},
                    { title: ' 提交用户', field: 'submitUser', align: 'left', width: "100px"},
                    { title: ' 提交时间', field: 'submitTime', align: 'left', width: "140px",template: WMS.UTILS.timestampFormat("submitTime")},
                    { title: ' 参考单号', field: 'referNo', align: 'left', width: "100px"},
                ],
                moveDataSource = wmsDataSource({
                   url:moveUrl,
                    schema:{
                        model:moveModel.move
                    },
                    callback:{
                        update:function (e) {
                            $scope.moveGrid.dataSource.read();
                        }
                    }
                });
            moveColumns = moveColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            moveColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);


            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource:moveDataSource,
                toolbar:[{name: "create",text:"新增",className:'btn-auth-add'},
                         { name: "submit",  template: '<a class="k-button k-button-submit" ng-click="submit(dataItem)">提交</a>', className:'btn-auth-submit'}
                ],
                columns:moveColumns,

                editable:{
                    mode:"popup",
                    window:{width:"600"},
                    template: kendo.template($("#move-kendo-template").html())
                },
                widgetId:"move",
                dataBound:function (e) {
                    var grid = this,
                        trs = grid.tbody.find(">tr");
                    _.each(trs,function (tr,i){
                        var record = grid.dataItem(tr);
                        if(record.statusCode !== 'Initial'){
                            $(tr).find(".k-button").remove();
                        }
                    });
                },
                customChange:function (grid) {
                    $(".k-button-submit").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.moveGrid);
                    if(selected.length >1){
                        kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据!");
                        $scope.moveGrid.dataSource.read();
                        return;
                    }

                    if (selected.length===1){
                        if(selected[0].statusCode === 'Initial'){
                            $(".k-button-submit").show();
                            }
                    }else {
                        $(".k-button-submit").show();
                    }
                }
            },$scope);


            //操作日志
            $scope.logOptions = wmsLog.operationLog;


            $scope.$on("kendoWidgetCreated", function (event, widget) {
                if (widget.options !== undefined && widget.options.widgetId === 'move') {
                    $scope.moveGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }
                //商品选择
               $scope.windowOpen = function (parentGrid) {
                   var cargoOwnerId = parentGrid.dataItem.cargoOwnerId;
                   var storageRoomId = parentGrid.dataItem.fromRoomId;
                   if(cargoOwnerId === undefined || cargoOwnerId === "" || cargoOwnerId === 0){
                       kendo.ui.ExtAlertDialog.showError("请先选择货主！");
                       return;
                   }
                   if (storageRoomId === undefined || storageRoomId === "" || storageRoomId === 0){
                       kendo.ui.ExtAlertDialog.showError("请先选择库房！");
                       return;
                   }
                    $scope.skuInventoryPopup.initParam = function (subScope) {
                        subScope.param = {
                            cargoOwnerId:cargoOwnerId,
                            storageRoomId : storageRoomId,
                        };
                    };
                    $scope.skuInventoryPopup.refresh().open().center();
                    $scope.skuInventoryPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        //赋值s
                        $scope.editModel.set("skuId", resultData.skuId);
                        $scope.editModel.set("skuName", resultData.itemName);
                        $scope.editModel.set("skuBarcode", resultData.barcode);
                        $scope.editModel.set("availableQty",resultData.onhandQty);

                        $("#movedQty").attr("disabled",false);

                    };
                }



                //移库"提交"操作
                $scope.submit = function () {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.moveGrid);
                    if(selectedData.length === 0){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        $scope.moveGrid.dataSource.read();
                        return;
                    }
                    var id = selectedData[0].id;
                    $sync(moveUrl+"/submit/"+id,"PUT").then(function (xhr) {
                        $scope.moveGrid.dataSource.read();
                    },function (xhr){
                        $scope.moveGrid.dataSource.read();
                    })
                }

            });

        }]);
})

