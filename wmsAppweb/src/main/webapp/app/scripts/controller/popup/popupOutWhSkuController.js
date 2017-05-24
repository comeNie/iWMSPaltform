
define(['scripts/controller/controller', '../../model/data/skuModel'], function(controller, skuModel) {
    "use strict";
    controller.controller('popupOutWhSkuController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var outWhSkuUrl = $url.dataOutWhSkuUrl,
                selectable = "row",
                OutWhSkuPopupGridWidgetId = "userPopup",
                OutWhSkuColumns = [
                    { title: '商品名称', field: 'itemName', align: 'left', width: "120px"},
                    { title: '商品条码', align: 'left',field: 'barcode', width: "120px"},
                    { title: '商品编码', align: 'left',field: 'sku', width: "120px"},
                    { title: '货主商品条码', align: 'left',field: 'cargoOwnerBarcode', width: "120px"},
                    { title: '商品规格', align: 'left',field: 'spec', width: "120px"},
                    { title: '商品单位', align: 'left',field: 'unitType', width: "120px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')}
                ];

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                widgetId: OutWhSkuPopupGridWidgetId,
                dataSource: {},
                columns: OutWhSkuColumns,
                editable: false,
                selectable: "row",
                height: 280
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){
                if (widget.options !== undefined && widget.options.widgetId === OutWhSkuPopupGridWidgetId) {
                    if (_.isFunction($scope.outWhSkuPopup.initParam)) {
                        $scope.outWhSkuPopup.initParam($scope);
                        if ($scope.selectable) {
                            selectable = $scope.selectable;
                        }
                        if ($scope.url) {
                            outWhSkuUrl = $scope.url;
                            widget.setOptions({selectable: selectable, columns: OutWhSkuColumns});
                        }
                        if($scope.param){
                            outWhSkuUrl = outWhSkuUrl +"/"+$scope.param;
                        }else {
                            outWhSkuUrl = outWhSkuUrl +"/"+0;
                        }
                        var outWhSkuDataSource = wmsDataSource({
                            url: outWhSkuUrl,
                            schema: {
                                model: skuModel.header
                            }
                        });
                        widget.setDataSource(outWhSkuDataSource);
                        widget.refresh();
                    }
                }
            });

            $scope.selectOutWhSku = function(){
                var selectView = $scope.outWhSkuPopupGrid.select();
                if (selectable !== "row") {
                    var selectedDataArray = _.map(selectView, function(view) {
                        return $scope.outWhSkuPopupGrid.dataItem(view);
                    });
                    if (_.isFunction($scope.outWhSkuPopup.setReturnData)) {
                        $scope.outWhSkuPopup.setReturnData(selectedDataArray);
                    }
                } else {
                    var selectedData = $scope.outWhSkuPopupGrid.dataItem(selectView);
                    if (_.isFunction($scope.outWhSkuPopup.setReturnData)) {
                        $scope.outWhSkuPopup.setReturnData(selectedData);
                    }
                }
                $scope.closeOutWhSkuWindow();
            };

            $scope.closeOutWhSkuWindow = function(){
                $scope.query = {};
                $scope.outWhSkuPopupGrid.dataSource.data([]);
                $scope.outWhSkuPopup.close();
            };

        }]);

});