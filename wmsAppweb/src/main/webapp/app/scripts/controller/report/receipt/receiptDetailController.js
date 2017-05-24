define(['scripts/controller/controller'], function (controller) {
    "use strict";
    controller.controller('receiptDetailController',
        ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',function ($scope, $rootScope, $sync, $url, wmsDataSource) {
            var receiptDetailUrl = $url.reportReceiptDetailUrl,
                receiptDetailColumns = [
                    { field: 'receiptId', title: '入库单号', filterable: false, align: 'left', width: '150px'},
                    { field: 'receiptDetailId', title: '明细单号', filterable: false, align: 'left', width: '150px'},
                    { field: 'cargoOwnerId', title: '货主',  align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { field: 'warehouseId', title: '配销中心', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.whFormat},
                    { field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '100px'},
                    { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '120px'},
                    { field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '100px'},
                    { field: 'unitType', title: '商品单位', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    {field: 'storageRoomId', title: '存储库房', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.storageRoomStringFormat("storageRoomId", "storageRoomSrc")},
                    {field:'receiptQty',title: '检收数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                    { field: 'receiptTime', title: '收货时间', filterable: false, align: 'left', width: "150px", template: WMS.UTILS.timestampFormat("receiptTime")},
                ],
                receiptDetailDataSource = wmsDataSource({
                    url:receiptDetailUrl,
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
                dataSource: receiptDetailDataSource,
                exportable: true,
                hasFooter: true,
                columns: receiptDetailColumns,
                editable: false
            }, $scope);
        }
        ]);
})
