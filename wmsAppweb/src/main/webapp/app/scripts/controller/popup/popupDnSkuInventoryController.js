
define(['scripts/controller/controller'], function(controller) {
    "use strict";
    controller.controller('popupDnSkuInventoryController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var dnSkuInventoryUrl = window.BASEPATH +'/report/inventory',
                selectable = "row",
                DnSkuInventoryPopupGridWidgetId = "userPopup",
                DnSkuInventoryColumns = [
                    { title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                    { field: 'storageRoomId', title: '库房', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.storageRoomFormat("storageRoomId", "storageRoomSrc")},
                    // { title: '商品条码', align: 'left',field: 'barcode', width: "120px"},
                    // { title: '商品编码', align: 'left',field: 'sku', width: "120px"},
                    { field: 'activeQty', title: '可用数量', filterable: false, align: 'left', width: '100px'},
                    // { title: '货主商品条码', align: 'left',field: 'cargoOwnerBarcode', width: "120px"},
                    { title: '商品规格', align: 'left',field: 'spec', width: "120px"},
                    { title: '商品单位', align: 'left',field: 'unitType', width: "120px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')}
                ];

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                widgetId: DnSkuInventoryPopupGridWidgetId,
                dataSource: {},
                columns: DnSkuInventoryColumns,
                editable: false,
                selectable: "row",
                height: 280
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){
                if (widget.options !== undefined && widget.options.widgetId === DnSkuInventoryPopupGridWidgetId) {
                    if (_.isFunction($scope.dnSkuInventoryPopup.initParam)) {
                        $scope.dnSkuInventoryPopup.initParam($scope);
                        if ($scope.selectable) {
                            selectable = $scope.selectable;
                        }
                        if ($scope.url) {
                            dnSkuInventoryUrl = $scope.url;
                            widget.setOptions({selectable: selectable, columns: DnSkuInventoryColumns});
                        }
                        var cargoOwnerId;
                        if($scope.param){
                            dnSkuInventoryUrl = dnSkuInventoryUrl +"/"+$scope.param;
                        }else {
                            dnSkuInventoryUrl = dnSkuInventoryUrl +"/"+0;
                        }
                        var dnSkuInventoryDataSource = wmsDataSource({
                            url: dnSkuInventoryUrl,
                        });
                        widget.setDataSource(dnSkuInventoryDataSource);
                        widget.refresh();
                    }
                }
            });

            $scope.selectDnSkuInventory = function(){
                var selectView = $scope.dnSkuInventoryPopupGrid.select();
                if (selectable !== "row") {
                    var selectedDataArray = _.map(selectView, function(view) {
                        return $scope.dnSkuInventoryPopupGrid.dataItem(view);
                    });
                    if (_.isFunction($scope.dnSkuInventoryPopup.setReturnData)) {
                        $scope.dnSkuInventoryPopup.setReturnData(selectedDataArray);
                    }
                } else {
                    var selectedData = $scope.dnSkuInventoryPopupGrid.dataItem(selectView);
                    if (_.isFunction($scope.dnSkuInventoryPopup.setReturnData)) {
                        $scope.dnSkuInventoryPopup.setReturnData(selectedData);
                    }
                }
                $scope.closeDnSkuInventoryWindow();
            };

            $scope.closeDnSkuInventoryWindow = function(){
                $scope.query = {};
                $scope.dnSkuInventoryPopupGrid.dataSource.data([]);
                $scope.dnSkuInventoryPopup.close();
            };

        }]);

});