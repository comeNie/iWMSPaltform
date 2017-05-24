define(['kendo'],
    function(kendo){
        'use strict';
        return {
            skuInventory:{
                id:"id",
                fields: {
                    skuId:{type:"number",editable: false, nullable: true},
                    cargoOwnerId:{type:"number"},
                    storageRoomId:{type:"number"},
                    itemName: {type:"string"},
                    sku: {type:"string"},
                    barcode: {type:"string"},
                    onhandQty:{type:"number"},
                    isActive: { type: "boolean"}
                }
            }
        };
    });