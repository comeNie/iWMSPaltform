define(['scripts/controller/controller'], function (controller) {
    "use strict";
    controller.controller('reportInventorySummaryController', 
        ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource', '$filter', function ($scope, $rootScope, $sync, $url, wmsDataSource, $filter) {
            var reportInventorySummaryUrl = $url.reportInventorySummaryUrl,
                InventorySummaryColumns = [
                    { field: 'cargoOwnerId', title: '货主',  align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { field: 'warehouseId', title: '仓库', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.whFormat},
                    { field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '100px'},
                    { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '150px'},
                    { field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '150px'},
                    { field: 'unitType', title: '商品单位', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { field: 'skuBarcode', title: '条码', filterable: false, align: 'left', width: '150px'},
                    { field: 'onhandQty', title: '在库数量',footerTemplate: "#: sum #",  filterable: false, align: 'left', width: '100px'},
                    { field: 'activeQty', title: '可用数量',footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px',excelExport:"calcParse:onhandQty-allocatedQty-pickedQty-mortgagedQty-workingQty-packageQty"},
                    { field: 'allocatedQty', title: '已分配数量',footerTemplate: "#: sum #",  filterable: false, align: 'left', width: '100px'},
                    { field: 'pickedQty', title: '已拣货数量',footerTemplate: "#: sum #",  filterable: false, align: 'left', width: '100px'},
                    { field: 'mortgagedQty', title: '已质押数量',footerTemplate: "#: sum #",  filterable: false, align: 'left', width: '100px'},
                    { field: 'workingQty', title: '在加工数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                    { field: 'packageQty', title: '在包装数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'}
                ],
                InventorySummaryDataSource = wmsDataSource({
                    url: reportInventorySummaryUrl,
                    aggregate: [
                        { field: "activeQty", aggregate: "sum" },
                        { field: "onhandQty", aggregate: "sum" },
                        { field: "allocatedQty", aggregate: "sum" },
                        { field: "pickedQty", aggregate: "sum" },
                        { field: "mortgagedQty", aggregate: "sum" },
                        { field: "workingQty", aggregate: "sum" },
                        { field: "packageQty", aggregate: "sum" }
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
                                record.activeQty = record.onhandQty - record.allocatedQty-record.mortgagedQty-record.workingQty;
                                return record;
                            });
                        }
                    }
                });
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: InventorySummaryDataSource,
                exportable: true,
                hasFooter: true,
                columns: InventorySummaryColumns,
                editable: false
            }, $scope);
            }
        ]);
})