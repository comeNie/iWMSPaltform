define(['kendo'],
    function(kendo){
        'use strict';
        return {
            proInventory:{
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    inventoryId: {type:"number" },
                    warehouseId: {type:"number" },
                    parentId: {type:"number" },
                    tenantId: {type:"number" },
                    cargoOwnerId: {type:"number" },
                    storageRoomId: {type:"number"},
                    skuId: {type:"number"},
                    skuName: {type:"string"},
                    sku:{type:"string"},
                    spec: {type:"string"},
                    unitType: {type:"string"},
                    statusCode: {type:"string"},
                    qty: {type:"number"},
                    workGroupNo: {type:"string"},
                    inventoryStatusCode: {type:"boolean"},
                    createUser: {type:"string"},
                    createTime: { type: "number"},
                    updateUser: {type:"string"},
                    updateTime:{ type: "number"}
                }
            },
            proInventorydetail:{
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    inventoryId: {type:"number" },
                    warehouseId: {type:"number" },
                    parentId: {type:"number" },
                    tenantId: {type:"number" },
                    cargoOwnerId: {type:"number" },
                    storageRoomId: {type:"number"},
                    skuId: {type:"number"},
                    skuName: {type:"string"},
                    sku:{type:"string"},
                    spec: {type:"string"},
                    unitType: {type:"string"},
                    statusCode: {type:"string"},
                    qty: {type:"number"},
                    workGroupNo: {type:"string"},
                    inventoryStatusCode: {type:"boolean"},
                    createUser: {type:"string"},
                    createTime: { type: "number"},
                    updateUser: {type:"string"},
                    updateTime:{ type: "number"}
                }
            }
        };
    });