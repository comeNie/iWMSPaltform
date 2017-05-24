define(['scripts/controller/controller'], function (controller) {
    "use strict";
    controller.controller('reportTransactionLogController', 
        ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource', '$filter', function ($scope, $rootScope, $sync, $url, wmsDataSource, $filter) {
            var reportInventoryLogUrl = $url.reportInventoryLogUrl,
                InventoryLogColumns = [
                    //{ field: 'warehouseId', title: '仓库', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.whFormat},
                    { field: 'storageRoomId', title: '库房', filterable: false, align: 'left', width: '100px',template: WMS.UTILS.storageRoomFormat("storageRoomId", "storageRoomSrc")},
                    { field: 'typeCode', title: '单据类型', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('typeCode','OrderType')},
                    { field: 'orderId', title: '操作单编号', filterable: false, align: 'left', width: '100px'},
                    { field: 'detailId', title: '操作单明细编号', filterable: false, align: 'left', width: '150px'},
                    { field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '100px'},
                    { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '150px'},
                    { field: 'skuSpec', title: '商品规格', filterable: false, align: 'left', width: '100px'},
                    { field: 'unitType', title: '商品单位', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { field: 'barCode', title: '条码', filterable: false, align: 'left', width: '150px'},
                    { field: 'qty', title: '改变数量', filterable: false, align: 'left', width: '100px'},
                    { field: 'createTime', title: '创建时间', filterable: false, align: 'left', width: "150px", template: WMS.UTILS.timestampFormat("createTime")},
                    { field: 'updateUser', title: '修改人', filterable: false, align: 'left', width: '100px'},
                    { field: 'updateTime', title: '修改时间', filterable: false, align: 'left', width: '150px', template: WMS.UTILS.timestampFormat("updateTime")}
                ],
                InventoryLogDataSource = wmsDataSource({
                    url: reportInventoryLogUrl,
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
                dataSource: InventoryLogDataSource,
                exportable: true,
                hasFooter: true,
                columns: InventoryLogColumns,
                editable: false
            }, $scope);

            }
        ]);
})