define(['scripts/controller/controller', '../../model/data/inventoryCargoOwnerModel'], function(controller,inventoryCargoOwnerModel) {
    "use strict";
    controller.controller('popupInventoryCargoOwnerController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var inventoryCargoOwnerUrl = $url.inventoryCargoOwnerUrl,
                selectable = "row",
                inventoryCargoOwnerPopupGridWidgetId = "inventoryCargoOwnerPopup",
                inventoryCargoOwnerColumns = [
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "80px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '商品名称', field: 'skuName', align: 'left', width: "100px"},
                    { title: '商品编码', field: 'sku', align: 'left', width: "140px"},
                    { title: '商品规格', field: 'spec', align: 'left', width: "140px"},
                    { title: '商品单位', field: 'unitType', align: 'left', width: "140px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { title: '存储库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                    { title: '可用数量', field: 'activeQty', align: 'left', width: "100px"},
                ];

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                widgetId:inventoryCargoOwnerPopupGridWidgetId,
                dataSource: {},
                columns: inventoryCargoOwnerColumns,
                editable: false,
                window:{
                    width:"700"
                },
                selectable: "row",
                height: 280
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){
                    if (widget.options !== undefined && widget.options.widgetId === inventoryCargoOwnerPopupGridWidgetId) {
                        if (_.isFunction($scope.inventoryCargoOwnerPopup.initParam)) {
                            $scope.inventoryCargoOwnerPopup.initParam($scope);
                            if ($scope.selectable) {
                                selectable = $scope.selectable;
                            }
                            if ($scope.url) {
                                inventoryCargoOwnerUrl = $scope.url;
                                widget.setOptions({selectable: selectable, columns: inventoryCargoOwnerColumns});
                            }
                            if ($scope.param) {
                                inventoryCargoOwnerUrl = inventoryCargoOwnerUrl + "/" + $scope.param;
                            } else {
                                inventoryCargoOwnerUrl = inventoryCargoOwnerUrl + "/" + 0;
                            }
                            var inventoryCargoOwnerDataSource = wmsDataSource({
                                url: inventoryCargoOwnerUrl,
                                schema: {
                                    model: inventoryCargoOwnerModel.inventoryCargoOwner
                                }
                            });
                            widget.setDataSource(inventoryCargoOwnerDataSource);
                            widget.refresh();
                        }
                    }
            });

            $scope.inventoryCargoOwner = function(){
                var selectView = $scope.inventoryCargoOwnerPopupGrid.select();
                var selectedData = $scope.inventoryCargoOwnerPopupGrid.dataItem(selectView);
                if (_.isFunction($scope.inventoryCargoOwnerPopup.setReturnData)) {
                    $scope.inventoryCargoOwnerPopup.setReturnData(selectedData);
                }
                $scope.closeInventoryCargoOwnerWindow();
            };

            $scope.closeInventoryCargoOwnerWindow = function(){
                $scope.query = {};
                $scope.inventoryCargoOwnerPopupGrid.dataSource.data([]);
                $scope.inventoryCargoOwnerPopup.close();
            };

        }]);

});
