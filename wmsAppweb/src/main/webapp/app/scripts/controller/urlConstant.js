define(['app'], function (app) {
        "use strict";
        app.constant('url', {

                //系统登录初始化相关URL
                naviUrl: window.BASEPATH + '/index/menu',
                permUrl: window.BASEPATH + '/index/perm',
                passwordUrl: window.BASEPATH + '/user/author/password',
                getWarehouseUrl: window.BASEPATH + '/index/warehouse/combox',
                switchWarehouseUrl: window.BASEPATH + '/index/warehouse/current',
                dataWarehouseUrl: window.BASEPATH + '/warehouse',
                getSystemDataWarehouseUrl:window.BASEPATH + "/index/warehouse",

                //基础数据相关URL
                dataSkuUrl: window.BASEPATH + '/sku/data',
                dataOrganizationsUrl: window.BASEPATH + '/organizations/data',
                dataSkuInventoryUrl:window.BASEPATH + '/inventory/adjustment/data',
                dataFrozenInventoryUrl:window.BASEPATH + '/factoring/frozen/data/inventory',
                dataOutWhSkuUrl:window.BASEPATH + '/sku/data/cargoOwner',
                dataInventoryUrl: window.BASEPATH + '/inventory',
                dataCustomerUrl: window.BASEPATH + '/customer/data',



                //系统设置模块相关URL
                systemTenantUrl: window.BASEPATH + '/tenant',
                systemWarehouseUrl: window.BASEPATH + '/warehouse',
                systemCodeHeaderUrl: window.BASEPATH + '/code/header',
                systemCodeDetailUrl: window.BASEPATH + '/code/detail',
                systemCodeFlushUrl: window.BASEPATH + '/code/flush',
                authorRoleUrl: window.BASEPATH + '/role',
                authorUserUrl: window.BASEPATH + '/user',
                authorModuleUrl: window.BASEPATH + '/module',
                systemZoneUrl: window.BASEPATH + '/zone',
                systemLocationUrl: window.BASEPATH + '/location',
                systemStorageRoomUrl: window.BASEPATH + '/storage/room',
                systemCargoOwnerUrl: window.BASEPATH + '/cargo/owner',
                systemSkuCategorysUrl:window.BASEPATH +'/sku/categorys',
                systemSkuUrl:window.BASEPATH +'/sku',
                systemCustomerUrl: window.BASEPATH + '/customer',
                systemSkuImportUrl:window.BASEPATH +'/sku/import',
                systemPrintMapUrl:window.BASEPATH +'/print/map',
                systemPrintTempUrl:window.BASEPATH +'/print/temp',
                systemOrganizationsUrl:window.BASEPATH +'/organizations',
                systemAllocationStrategyUrl:window.BASEPATH + '/allocation/strategy',



                //入库业务相关URL
                inwhReceiptHeaderUrl: window.BASEPATH + '/receipt',
                inwhAsnHeaderUrl: window.BASEPATH + '/asn',
                inwhQcHeaderUrl: window.BASEPATH + '/qc',
                inwhqcCheckUrl: window.BASEPATH + '/qcCheck',
                inwhAdjustmentHeaderUrl: window.BASEPATH + '/inventory/adjustment',
                inwhStocktakingHeaderUrl:window.BASEPATH + '/inventory/stocktaking',

                //出库相关URL
                outwhShipmentHeaderUrl:window.BASEPATH + '/shipment',
                outwhDnHeaderUrl:window.BASEPATH + '/dn/header',
                outwhFrozenUrl:window.BASEPATH +'/factoring/frozen',
                outwhUnfrozenUrl:window.BASEPATH +'/factoring/unfrozen',
                outwhWaveUrl:window.BASEPATH +'/wave',
                outwhProInventoryUrl:window.BASEPATH +'/proInventory',
                outwhProducerProcessUrl:window.BASEPATH +'/producer',


                //报表相关URL
                reportInventoryUrl:window.BASEPATH +'/report/inventory',
                reportInventorySummaryUrl:window.BASEPATH +'/report/inventorySummary',
                reportInventoryLogUrl:window.BASEPATH +'/report/inventoryLog',
                reportOperateLogUrl:window.BASEPATH +'/log/operationLog',
                reportReceiptDetailUrl:window.BASEPATH +'/report/receipt/detail',
                reportReceiptSummaryUrl1:window.BASEPATH +'/report/receipt/summary',
                reportOutDetailUrl:window.BASEPATH +'/report/out/detail',
                reportOutSummaryUrl:window.BASEPATH +'/report/out/summary',
                reportSalesUrl:window.BASEPATH +'/report/integration/reportSales',


                //库内相关
                systemTemplateListUrl: window.BASEPATH + '/report/template',
                inventoryCycleHeaderUrl: window.BASEPATH + '/inventory/check',
                inventoryFrozeHeaderUrl: window.BASEPATH + '/inventory/state',
                inventoryUnfrozeHeaderUrl: window.BASEPATH + '/inventory/unfroze',
                inventoryAdjustUrl: window.BASEPATH + '/inventory/adjust',
                inventoryStatusAdjustUrl: window.BASEPATH + '/inventory/adjust',
                inventoryTransferUrl: window.BASEPATH + '/inventory/transfer',
                inventoryReplenishUrl: window.BASEPATH + '/inventory/replenish',
                inventoryMoveUrl: window.BASEPATH + '/inventory/move',
                inventoryCountUrl: window.BASEPATH + '/inventoryCount',
                inventoryLogUrl: window.BASEPATH + '/inventory/log',
                inventoryproPackageUrl: window.BASEPATH + '/inventory/proPackage',
                inventoryCargoOwnerUrl: window.BASEPATH + '/inventory/popup',
                inventoryProInvPackageUrl:window.BASEPATH + '/proInvPackage',

                //策略获取库房
                storageRoomIdUrl: window.BASEPATH + '/index/storageRoom',

        });

});
