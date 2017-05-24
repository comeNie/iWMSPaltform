define(['kendo'],
    function(kendo){
        'use strict';
        return {
            inventoryCargoOwner:{
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    tenantId: {type:"number" },
                    warehouseId: {type:"number" },
                    cargoOwnerId: {type:"number" },
                    skuId: {type:"number" },
                    itemName: {type:"string"},
                    sku: {type:"string"},
                    spec:{type:"string"},
                    unitType:{type:"string"},
                    barcode: {type:"string"},
                    storageRoomId: {type:"number" },
                    zoneId: {type:"number" },
                    locationId: {type:"number" },
                    workGroupNo: {type:"String" },
                    containerNo: {type:"String" },
                    palletNo: {type:"String" },
                    price: {type:"number" },
                    onhandQty: {type:"number" },
                    allocatedQty: {type:"number" },
                    pickedQty: {type:"number" },
                    version: {type:"number" },
                    mortgagedQty: {type:"number" },
                    isHold: {type:"boolean" },
                    inventoryStatusCode: {type:"string"},
                    createUser: {type:"string"},
                    createTime: { type: "number"},
                    updateUser: {type:"string"},
                    updateTime:{ type: "number"}
                }
            }
        };
    });