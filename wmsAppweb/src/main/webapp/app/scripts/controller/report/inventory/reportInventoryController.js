define(['scripts/controller/controller'], function (controller) {
    "use strict";
    controller.controller('reportInventoryController',
        ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',function ($scope, $rootScope, $sync, $url, wmsDataSource) {
                var reportInventoryUrl = $url.reportInventoryUrl,
                    inventoryColumns = [
                        { field: 'cargoOwnerId', title: '货主',  align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                        { field: 'warehouseId', title: '仓库', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.whFormat},
                        { field: 'storageRoomId', title: '库房', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.storageRoomFormat("storageRoomId", "storageRoomSrc")},
                        { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '150px'},
                        { field: 'skuBarcode', title: '条码', filterable: false, align: 'left', width: '150px'},
                        { field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '100px'},
                        { field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '100px'},
                        { field: 'unitType', title: '商品单位', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                        { field: 'onhandQty', title: '在库数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                        { field: 'activeQty', title: '可用数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                        { field: 'allocatedQty', title: '已分配数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                        { field: 'pickedQty', title: '已拣货数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                        { field: 'mortgagedQty', title: '已质押数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                        { field: 'workingQty', title: '在加工数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                        { field: 'packageQty', title: '在包装数量', footerTemplate: "#: sum #", filterable: false, align: 'left', width: '100px'},
                        { field: 'isHold', title: '是否冻结', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.checkboxDisabledTmp('isHold', 'yesOrNoHoldFormat')},
                        { field: 'createTime', title: '入库时间', filterable: false, align: 'left', width: '150px', template: WMS.UTILS.timestampFormat("createTime")},
                        { field: 'receiptTime', title: '收货时间', filterable: false, align: 'left', width: "150px", template: WMS.UTILS.timestampFormat("receiptTime")},
                        { field: 'updateUser', title: '修改人', filterable: false, align: 'left', width: '100px'},
                        { field: 'updateTime', title: '修改时间', filterable: false, align: 'left', width: '150px', template: WMS.UTILS.timestampFormat("updateTime")}
                    ],
                    inventoryDataSource = wmsDataSource({
                        url: reportInventoryUrl,
                        aggregate: [
                            { field: "activeQty", aggregate: "sum" },
                            { field: "onhandQty", aggregate: "sum" },
                            { field: "pickedQty", aggregate: "sum" },
                            { field: "allocatedQty", aggregate: "sum" },
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
                            // parse: function (data) {
                            //     return _.map(data, function (record) {
                            //         record.activeQty = record.onhandQty - record.allocatedQty-record.mortgagedQty;
                            //         return record;
                            //     });
                            // }

                        }
                    });

                $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                    dataSource: inventoryDataSource,
                    exportable: true,
                    hasFooter: true,
                    columns: inventoryColumns,
                    editable: false
                }, $scope);
            }
        ]);
})