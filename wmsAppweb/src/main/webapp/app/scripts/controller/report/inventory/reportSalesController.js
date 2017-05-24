define(['scripts/controller/controller'],function (controller) {
    "user strict";
    controller.controller('reportSalesController',
        ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',function ($scope, $rootScope, $sync, $url, wmsDataSource){
                var reportSalesUrl = $url.reportSalesUrl,
                    reportSalesColumns = [
                        { title: "出入库报表",columns:  [
                            {title: "物料", columns:[
                                { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '120px'},
                                { field: 'skuBarCode', title: '条码', filterable: false, align: 'left', width: '120px'},
                                { field: 'skuItemName', title: '商品名称', filterable: false, align: 'left', width: '120px'},
                                { field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '120px'},
                                { field: 'unitType', title: '商品单位', filterable: false, align: 'left', width: '120px',template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                                { field: 'categorysId', title: '商品类别', filterable: false, align: 'left', width: '120px',template:WMS.UTILS.skuCategorysFormat("categorysId")},
                            ]},
                            {title: "期初", columns:[
                                { field: 'startQty', title: '期初数量', filterable: false, align: 'left', width: '120px'},
                                { field: 'startAmount', title: '期初单价', filterable: false, align: 'left', width: '120px'},
                                { field: 'startPrice', title: '期初金额', filterable: false, align: 'left', width: '120px'},
                            ]},
                            {title: "入库", columns:[
                                { field: 'receivedQty', title: '入库数量', filterable: false, align: 'left', width: '120px'},
                                { field: 'costPrice', title: '入库单价', filterable: false, align: 'left', width: '120px'},
                                { field: 'totalPrice', title: '入库金额', filterable: false, align: 'left', width: '120px'},
                                { field: 'proReceiptQty', title: '加工入库数量', filterable: false, align: 'left', width: '120px'},
                                { field: 'proReceiptAmount', title: '加工入库单价', filterable: false, align: 'left', width: '120px'},
                                { field: 'proReceiptPrice', title: '加工入库金额', filterable: false, align: 'left', width: '120px'},
                            ]},
                            {title: "出库",  columns:[
                                { field: 'orderedQty', title: '出库数量', filterable: false, align: 'left', width: '120px'},
                                { field: 'amount', title: '出库单价', filterable: false, align: 'left', width: '120px'},
                                { field: 'proShipmentQty', title: '加工出库数量', filterable: false, align: 'left', width: '120px'},
                                { field: 'proShipmentAmount', title: '加工出库单价', filterable: false, align: 'left', width: '120px'},
                                { field: 'proShipmentPrice', title: '加工出库金额', filterable: false, align: 'left', width: '120px'},
                            ]},
                            {title: "结存", columns:[
                                { field: 'sumQty', title: '结算数量', filterable: false, align: 'left', width: '120px'},
                                { field: 'sumAmount', title: '结算单价', filterable: false, align: 'left', width: '120px'},
                                { field: 'sumPrice', title: '结算金额', filterable: false, align: 'left', width: '120px'},
                            ]},
                        ]},
                    ],
                    reportSalesDataSource = wmsDataSource({
            url: reportSalesUrl,
            schema: {
                model: {
                    id: "id",
                    fields: {
                        skuId: { type: "number", editable: false, nullable: true }
                    }
                },
            }
        });
        $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
            dataSource:reportSalesDataSource,
            exportable: true,
            hasFooter: true,
            columns: reportSalesColumns,
            editable:false
        },$scope);
        }

    ]);
})