define(['scripts/controller/controller', '../../model/data/inventoryModel'], function(controller) {
    "use strict";
    controller.controller('popupInventoryController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var inventoryUrl = $url.dataInventoryUrl,
                inventoryColumns = [
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "80px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '商品名称', field: 'skuName', align: 'left', width: "100px"},
                    { title: '商品编码', field: 'sku', align: 'left', width: "140px"},
                    { title: '商品规格', field: 'spec', align: 'left', width: "140px"},
                    { title: '商品单位', field: 'unitType', align: 'left', width: "140px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { title: '存储库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                    { title: '可用数量', align: 'left',field: 'activeQty', width: "100px"},
                ];
            var userDataSource = wmsDataSource({
                url: inventoryUrl,
                schema: {
                    model: {
                        id: 'id',
                        field: {
                            id: {type:"number", editable: false, nullable: true }
                        }
                    }
                }
            });


            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: userDataSource,
                columns: inventoryColumns,
                editable: false,
                selectable: "row",
                height: 280
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){

            });
            $scope.selectSku = function(){
                var selectView = $scope.inventoryPopupGrid.select();
                var selectedData = $scope.inventoryPopupGrid.dataItem(selectView);
                if (_.isFunction($scope.inventoryPopup.setReturnData)) {
                    $scope.inventoryPopup.setReturnData(selectedData);
                }
                $scope.closeSkuWindow();
            };

            $scope.closeSkuWindow = function(){
                $scope.query = {};
                $scope.inventoryPopupGrid.dataSource.data([]);
                $scope.inventoryPopup.close();
            };

        }]);

});
