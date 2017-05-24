
define(['scripts/controller/controller', '../../model/data/skuModel'], function(controller, skuModel) {
    "use strict";
    controller.controller('popupSkuController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var skusUrl = $url.dataSkuUrl,
                selectable = "row",
                skuPopupGridWidgetId = "userPopup",
                skusColumns = [
                    //{ title: '货主', field: 'cargoOwnerId', align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '商品名称', field: 'itemName', align: 'left', width: "120px"},
                    { title: '商品条码', align: 'left',field: 'barcode', width: "120px"},
                    { title: '商品编码', align: 'left',field: 'sku', width: "120px"},
                    { title: '商品规格', align: 'left',field: 'spec', width: "120px"},
                    { title: '单位类型', align: 'left',field: 'unitType', width: "120px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { title: '是否可用', align: 'left',field: 'isActive', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isActive")},
                ];

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                widgetId: skuPopupGridWidgetId,
                dataSource: {},
                columns: skusColumns,
                editable: false,
                selectable: "row",
                height: 280
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){
                if (widget.options !== undefined && widget.options.widgetId === skuPopupGridWidgetId) {
                        if ($scope.selectable) {
                            selectable = $scope.selectable;
                        }
                        if ($scope.url) {
                            skusUrl = $scope.url;
                            widget.setOptions({selectable: selectable,columns:skusColumns});
                        }
                        var skusDataSource = wmsDataSource({
                            url: skusUrl,
                            schema: {
                                model: skuModel.header
                            }
                        });
                        widget.setDataSource(skusDataSource);
                        widget.refresh();
                }
            });

            $scope.selectSku = function(){
                var selectView = $scope.skuPopupGrid.select();
                if (selectable !== "row") {
                    var selectedDataArray = _.map(selectView, function(view) {
                        return $scope.skuPopupGrid.dataItem(view);
                    });
                    if (_.isFunction($scope.skuPopup.setReturnData)) {
                        $scope.skuPopup.setReturnData(selectedDataArray);
                    }
                } else {
                    var selectedData = $scope.skuPopupGrid.dataItem(selectView);
                    if (_.isFunction($scope.skuPopup.setReturnData)) {
                        $scope.skuPopup.setReturnData(selectedData);
                    }
                }

                $scope.closeSkuWindow();
            };

            $scope.closeSkuWindow = function(){
                $scope.query = {};
                $scope.skuPopupGrid.dataSource.data([]);
                $scope.skuPopup.close();
            };

        }]);

});