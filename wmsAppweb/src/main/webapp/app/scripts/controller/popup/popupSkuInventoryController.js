
define(['scripts/controller/controller', '../../model/data/skuInventoryModel'], function(controller, skuInventoryModel) {
    "use strict";
    controller.controller('popupSkuInventoryController', ['$scope','$rootScope','sync','url','wmsDataSource',
            function($scope, $rootScope, $sync, $url, wmsDataSource) {
                var skuInventoryUrl = $url.dataSkuInventoryUrl,
                    selectable = "row",
                    skuInventoryPopupGridWidgetId = "userPopup",
                    skuInventoryColumns = [
                        { title: '货主', field: 'cargoOwnerId', align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                        { title: '商品ID', field: 'skuId', align: 'left', width: "120px"},
                        { title: '商品名称', field: 'itemName', align: 'left', width: "120px"},
                        { title: '商品编码', align: 'left',field: 'sku', width: "120px"},
                        { title: '商品条码', align: 'left',field: 'barcode', width: "120px"},
                        { title: '可用数量', align: 'left',field: 'onhandQty', width: "120px"},
                    ];

                $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                    widgetId: skuInventoryPopupGridWidgetId,
                    dataSource: {},
                    columns: skuInventoryColumns,
                    editable: false,
                    selectable: "row",
                    height: 280
                }, $scope);

                $scope.$on("kendoWidgetCreated", function(event, widget){
                    if (widget.options !== undefined && widget.options.widgetId === skuInventoryPopupGridWidgetId) {
                        if (_.isFunction($scope.skuInventoryPopup.initParam)) {
                            $scope.skuInventoryPopup.initParam($scope);
                            if ($scope.selectable) {
                                selectable = $scope.selectable;
                            }
                            if ($scope.url) {
                                 skuInventoryUrl = $scope.url;
                                 widget.setOptions({selectable: selectable,columns:skuInventoryColumns});
                            }
                            if ($scope.param) {
                                skuInventoryUrl = skuInventoryUrl+ "/"+ $scope.param.cargoOwnerId+"/"+$scope.param.storageRoomId;
                            }else{
                                skuInventoryUrl = skuInventoryUrl + "/" + 0;
                            }
                            var skuInventoryDataSource = wmsDataSource({
                                url: skuInventoryUrl,
                                schema: {
                                    model: skuInventoryModel.header
                                }
                            });
                            widget.setDataSource(skuInventoryDataSource);
                            widget.refresh();
                        }
                    }
                });


                $scope.skuInventory = function(){
                    var selectView = $scope.skuInventoryPopupGrid.select();
                    if (selectable !== "row") {
                        var selectedDataArray = _.map(selectView, function(view) {
                            return $scope.skuInventoryPopupGrid.dataItem(view);
                        });
                        if (_.isFunction($scope.skuInventoryPopup.setReturnData)) {
                            $scope.skuInventoryPopup.setReturnData(selectedDataArray);
                        }
                    } else {
                        var selectedData = $scope.skuInventoryPopupGrid.dataItem(selectView);
                        if (_.isFunction($scope.skuInventoryPopup.setReturnData)) {
                            $scope.skuInventoryPopup.setReturnData(selectedData);
                        }
                    }
                    $scope.closeSkuInventoryWindow();
                };

                $scope.closeSkuInventoryWindow = function(){
                    $scope.query = {};
                    $scope.skuInventoryPopupGrid.dataSource.data([]);
                    $scope.skuInventoryPopup.close();
                };

            }]);

});