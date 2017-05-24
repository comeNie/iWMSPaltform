define(['scripts/controller/controller'], function (controller) {
    "use strict";
    controller.controller('receiptSummaryController',
        ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',function ($scope, $rootScope, $sync, $url, wmsDataSource) {
            var receiptSummaryUrl = $url.reportReceiptSummaryUrl1,
                receiptSummaryColumns = [
                    { field: 'cargoOwnerId', title: '货主',  align: 'left', width: "60px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { field: 'warehouseId', title: '配销中心', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.whFormat},
                    { field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '100px'},
                    { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '100px'},
                    { field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '100px'},
                    { field: 'unitType', title: '商品单位', filterable: false, align: 'left', width: '80px',template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    {field:'receiptQty',title: '检收数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                ],
                receiptSummaryDataSource = wmsDataSource({
                    url:receiptSummaryUrl,
                    aggregate: [
                        { field: "receiptQty", aggregate: "sum" },
                    ],
                    schema: {
                        model: {
                            id: "id",
                            fields: {
                                id: { type: "number", editable: false, nullable: true }
                            }
                        },
                    }
                });

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: receiptSummaryDataSource,
                exportable: true,
                hasFooter: true,
                columns: receiptSummaryColumns,
                editable: false
            }, $scope);
        }
        ]);
})

