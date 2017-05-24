define(['kendo'],
    function(kendo) {
        'use strict';
        return {
            proPackage: {
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    tenantId: {type:"number" },
                    warehouseId: {type:"number" },
                    cargoOwnerId: {type: "number"},
                    storageRoomId:{type:"number"},
                    skuId: {type:"number"},
                    spec:{type:"String"},
                    qty: {type: "number"},
                    workGroupNo:{type:"String"},
                    statusCode: {type: "String"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"}
                }
            },
            proPackageDetail:{
                id:"id",
                fields: {
                    id:{type: "number",editable:false,nullable:false},
                    parentId: {type:"number" },
                    tenantId: {type:"number" },
                    warehouseId: {type:"number" },
                    proInventoryId: {type:"number" },
                    proInventoryQty: {type: "number"},
                    proStorageRoomId: {type: "number"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"},
                    itemName: {type:"string"},
                    sku: {type:"string"},
                    spec:{type:"string"},
                    unitType:{type:"string"},
                    barcode: {type:"string"}
                }
            }


        };
    });