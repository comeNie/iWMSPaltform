define(['scripts/controller/controller','../../model/system/roleModel'], function(controller, roleModel) {
    "use strict";
    controller.controller('roleController', ['$scope', '$rootScope', 'sync', 'url','wmsDataSource',
            function($scope, $rootScope, $sync,$url,wmsDataSource) {
                var roleUrl = $url.authorRoleUrl,
                    roleColumns = [
                        { title: '操作', command:[
                            { name: "grant", text:"授权", className: "btn-auth-grant", click:function(e){$scope.grantPermission(e,this);}},
                            { name: "edit", className: "btn-auth-edit", text: { edit: "编辑", cancel: "取消", update: "保存" } },
                            WMS.GRIDUTILS.deleteButton ],
                            width:"180px"
                        },
                        { title: '角色名称', field: 'roleName',align: 'left', width: "150px", filterable: { cell: {enabled: true, delay: 1500 }}},
                        { title: '是否可用',filterable: false,  field: 'isActive', template:WMS.UTILS.checkboxDisabledTmp("isActive"), align: 'left', width: "75px"}
                    ],
                    roleDataSource =  wmsDataSource({
                        url: roleUrl,
                        schema: {
                            model: roleModel.header
                        }
                    });
                roleColumns = roleColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
                $scope.mainGridOptions =  WMS.GRIDUTILS.getGridOptions({
                    dataSource: roleDataSource,
                    toolbar: [{ name: "create", text: "新增", className: "btn-auth-add"}],
                    columns: roleColumns,
                    editable: {
                        mode: "popup",
                        window: {
                            width: "380px"
                        },
                        template: kendo.template($("#roleEditor").html())
                    }
                }, $scope);

                var detailColumns=[
                    {filterable: false, title: '登录账户', field: 'userName', align: 'left', width: "200px"},
                    {filterable: false, title: '用户姓名', field: 'realName', align: 'left', width: "200px"},
                    { filterable: false, title: '系统管理员', field: 'isAdmin', template: WMS.UTILS.checkboxDisabledTmp("isAdmin"), align: 'left', width: "100px"}
                ];

                $scope.roleUserOptions = function (dataItem) {
                    var defaultOptions = {
                        dataSource: wmsDataSource({
                            url: roleUrl + "/" + dataItem.id + "/user",
                            pageSize: 30
                        }),
                        columns: detailColumns
                    };
                    return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
                };

                //角色授权
                $scope.grantPermission = function(e, gridWidgets){
                    e.preventDefault();
                    var dataItem = gridWidgets.dataItem($(e.currentTarget).closest("tr"));
                    $scope.grantPop.selectDataItem = dataItem;
                    $scope.grantPop.content(kendo.template($("#grantPop-template").html()));
                    $scope.grantPop.center().open();
                };

                $scope.search = function() {
                    var condition = {"roleName":$scope.roleName};
                    $scope.roleGrid.dataSource.filter(condition);
                    $scope.roleGrid.refresh();
                };


            }]).controller('grantAuthorRoleController',  ['$scope', '$rootScope', 'url','sync',
              function ($scope, $rootScope, $url,$sync) {
                var parent = $scope.$parent;
                $scope.model = parent.grantPop.selectDataItem;
                var authorRoleUrl = $url.authorRoleUrl;
                var roleId =$scope.model.id;
                $scope.naviOptions = {
                    dataTextField: "name",
                    checkboxes: {
                        checkChildren: true
                    }
                };
               $sync(authorRoleUrl+"/"+roleId+"/module","GET")
               .then(function(data){
                   if ($scope.tree) {
                       $scope.tree.dataSource.data(kendo.observableHierarchy(WMS.UTILS.processTreeData(data.result.rows, "id", "parentId", 0, true)));
                       $scope.tree.expand(".k-item");
                   } else {
                       $scope.treeData = kendo.observableHierarchy(WMS.UTILS.processTreeData(data.result.rows, "id", "parentId", 0, true));
                   }
               });

                $scope.saveRoleAction = function(){
                    var treeObj = $scope.tree;
                    var data = treeObj.dataSource.view();
                    $scope.selData=[];
                    $scope.getCheckedNode(data);
                    var authorModuleUrl = $url.authorRoleUrl;
                    $sync(authorModuleUrl+"/"+roleId+"/module","POST",{
                            data: {
                                roleId:$scope.model.id,
                                actions: _.uniq($scope.selData)
                            }
                    }).then(function(){
                            $scope.closePop();
                    });
                };

                $scope.expandAll = function() {
                    $scope.tree.expand(".k-item");
                };
                $scope.collapseAll = function() {
                    $scope.tree.collapse(".k-item");
                };

                $scope.getCheckedNode=function(data){
                    for(var i = 0;i < data.length;i++){
                        var value = data[i];
                        if(value.checked){
                            $scope.selData.push(value.id);
                        }

                        if(value.items !== undefined && value.items.length > 0){
                            if (hasCheckChildren(value.items)) {
                                $scope.selData.push(value.id);
                            }
                            $scope.getCheckedNode(value.items);
                        }
                    }

                };
                function hasCheckChildren(children) {
                  var has = false;
                  _.each(children, function(child){
                    if (child.checked) {
                      has = true;
                    }
                    if (!has && child.items !== undefined && child.items.length > 0) {
                      has = hasCheckChildren(child.items);
                    }
                    if (has) {
                      return has;
                    }
                  });

                  return has;
                }
                $scope.closePop = function(){
                    parent.grantPop.close();
                };

            }]);


})