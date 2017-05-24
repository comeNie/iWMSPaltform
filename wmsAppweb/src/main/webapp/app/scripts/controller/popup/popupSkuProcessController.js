
define(['scripts/controller/controller', '../../model/data/skuModel'], function(controller, skuModel) {
    "use strict";
    controller.controller('popupSkuProcessController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var skuUrl = $url.dataSkuUrl,
                selectable = "row",
                skuProcessPopupGridWidgetId = "userPopup",
                skuColumns = [
                    { title: '商品Id', field: 'id', align: 'left', width: "80px"},
                    { title: '商品名称', field: 'itemName', align: 'left', width: "120px"},
                    { title: '商品条码', align: 'left',field: 'barcode', width: "120px"},
                    { title: '商品编码', align: 'left',field: 'sku', width: "120px"},
                    { title: '商品规格', align: 'left',field: 'spec', width: "120px"},
                    { title: '单位类型', align: 'left',field: 'unitType', width: "120px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                ];

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                widgetId: skuProcessPopupGridWidgetId,
                dataSource: {},
                columns: skuColumns,
                editable: false,
                selectable: "row",
                height: 280
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){
                if (widget.options !== undefined && widget.options.widgetId === skuProcessPopupGridWidgetId) {
                    if (_.isFunction($scope.skuProcessPopup.initParam)) {
                        $scope.skuProcessPopup.initParam($scope);
                        if ($scope.selectable) {
                            selectable = $scope.selectable;
                        }
                        if ($scope.url) {
                            skuUrl = $scope.url;
                            widget.setOptions({selectable: selectable,columns:skuColumns});
                        }
                        if ($scope.param) {
                            skuUrl = skuUrl+ "/"+ $scope.param;
                        }else{
                            skuUrl = skuUrl + "/" + 0;
                        }
                        var skuDataSource = wmsDataSource({
                            url: skuUrl,
                            schema: {
                                model: skuModel.header
                            }
                        });
                        widget.setDataSource(skuDataSource);
                        widget.refresh();
                    }
                }
            });

            $scope.selectSku = function(){
                var selectView = $scope.skuProcessPopupGrid.select();
                if (selectable !== "row") {
                    var selectedDataArray = _.map(selectView, function(view) {
                        return $scope.skuProcessPopupGrid.dataItem(view);
                    });
                    if (_.isFunction($scope.skuProcessPopup.setReturnData)) {
                        $scope.skuProcessPopup.setReturnData(selectedDataArray);
                    }
                } else {
                    var selectedData = $scope.skuProcessPopupGrid.dataItem(selectView);
                    if (_.isFunction($scope.skuProcessPopup.setReturnData)) {
                        $scope.skuProcessPopup.setReturnData(selectedData);
                    }
                }
                $scope.closeSkuWindow();
            };

            $scope.closeSkuWindow = function(){
                $scope.skuProcessPopup.close();
            };

        }]);

});