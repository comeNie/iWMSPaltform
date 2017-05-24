define(['scripts/controller/controller', '../../model/data/frozenInventoryModel'], function(controller, frozenInventoryModel) {
    "use strict";
    controller.controller('popupFrozenInventoryController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var frozenInventoryUrl = $url.dataFrozenInventoryUrl,
                selectable = "row",
                frozenInventoryPopupGridWidgetId = "frozenInventoryPopup",
                frozenInventoryColumns = [
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "80px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '商品名称', field: 'itemName', align: 'left', width: "80px"},
                    { title: '商品编码', field: 'sku', align: 'left', width: "110px"},
                    { title: '商品规格', align: 'left',field: 'spec', width: "100px"},
                    { title: '单位类型', align: 'left',field: 'unitType', width: "100px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { title: '存储库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                    { title: '可用数量', align: 'left',field: 'mortgagedAbleQty', width: "100px"},
                    { title: '在库数量', align: 'left',field: 'onhandQty', width: "100px"},
                    { title: '已质押数', align: 'left',field: 'mortgagedQty', width: "100px"},
                    { title: '已拣货数', align: 'left',field: 'pickedQty', width: "100px"},
                    { title: '在加工数', align: 'left',field: 'workingQty', width: "100px"},

                ];

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                widgetId: frozenInventoryPopupGridWidgetId,
                dataSource: {},
                columns: frozenInventoryColumns,
                editable: false,
                selectable: "row",
                height: 280
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){
                if (widget.options !== undefined && widget.options.widgetId === frozenInventoryPopupGridWidgetId) {
                    if (_.isFunction($scope.frozenInventoryPopup.initParam)) {
                        $scope.frozenInventoryPopup.initParam($scope);
                        if ($scope.selectable) {
                            selectable = $scope.selectable;
                        }
                        if ($scope.url) {
                            frozenInventoryUrl = $scope.url;
                            widget.setOptions({selectable: selectable,columns:frozenInventoryColumns});
                        }
                        if ($scope.param) {
                            frozenInventoryUrl = frozenInventoryUrl+ "/"+ $scope.param;
                        }else{
                            frozenInventoryUrl = frozenInventoryUrl + "/" + 0;
                        }
                        var frozenInventoryDataSource = wmsDataSource({
                            url: frozenInventoryUrl,
                            schema: {
                                model: frozenInventoryModel.frozenInventory
                            }
                        });
                        widget.setDataSource(frozenInventoryDataSource);
                        widget.refresh();
                    }
                }
            });


            $scope.frozenInventory = function(){
                var selectView = $scope.frozenInventoryPopupGrid.select();
                if (selectable !== "row") {
                    var selectedDataArray = _.map(selectView, function(view) {
                        return $scope.frozenInventoryPopupGrid.dataItem(view);
                    });
                    if (_.isFunction($scope.frozenInventoryPopup.setReturnData)) {
                        $scope.organizationsPopup.setReturnData(selectedDataArray);
                    }
                } else {
                    var selectedData = $scope.frozenInventoryPopupGrid.dataItem(selectView);
                    if (_.isFunction($scope.frozenInventoryPopup.setReturnData)) {
                        $scope.frozenInventoryPopup.setReturnData(selectedData);
                    }
                }
                $scope.closeFrozenInventoryWindow();
            };

            $scope.closeFrozenInventoryWindow = function(){
                $scope.query = {};
                $scope.frozenInventoryPopupGrid.dataSource.data([]);
                $scope.frozenInventoryPopup.close();
            };

        }]);

});
