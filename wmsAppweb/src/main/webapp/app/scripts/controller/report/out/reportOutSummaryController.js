define(['scripts/controller/controller'], function (controller) {
    "use strict";
    controller.controller('reportOutSummaryController',
        ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource', '$filter', function ($scope, $rootScope, $sync, $url, wmsDataSource, $filter) {
            var reportOutSummaryUrl = $url.reportOutSummaryUrl,
                outSummaryColumns = [
                    { field: 'cargoOwnerId', title: '货主',  align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    //{ field: 'warehouseId', title: '仓库', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.whFormat},
                    { field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '100px'},
                    { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '150px'},
                    { field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '150px'},
                    { field: 'unitType', title: '商品单位', filterable: false, align: 'left', width: '150px',template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { field: 'skuBarcode', title: '条码', filterable: false, align: 'left', width: '150px'},
                    { field: 'orderedQty', title: '期望出库数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                    { field: 'allocatedQty', title: '已分配数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                    { field: 'shippedQty', title: '已出库数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                ],
                outSummaryDataSource = wmsDataSource({
                    url: reportOutSummaryUrl,
                    aggregate: [
                        { field: "orderedQty", aggregate: "sum" },
                        { field: "allocatedQty", aggregate: "sum" },
                        { field: "shippedQty", aggregate: "sum" }
                    ],
                    schema: {
                        model: {
                            id: "id",
                            fields: {
                                id: { type: "number", editable: false, nullable: true }
                            }
                        },
                        parse: function (data) {
                            return _.map(data, function (record) {
                                record.activeQty = record.onhandQty - record.allocatedQty-record.mortgagedQty;
                                return record;
                            });
                        }
                    }
                });
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: outSummaryDataSource,
                exportable: true,
                hasFooter: true,
                columns: outSummaryColumns,
                editable: false
            }, $scope);
            }
        ]);
})