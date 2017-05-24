define(['kendo'],
    function(kendo){
        'use strict';
        return {
            frozenInventory:{
                id:"id",
                fields: {
                    skuId:{type:"number",editable: false, nullable: true},
                    inventoryId:{type:"number"},
                    cargoOwnerId:{type:"number"},
                    storageRoomId:{type:"number"},
                    itemName: {type:"string"},
                    sku: {type:"string"},
                    spec:{type:"string"},
                    unitType:{type:"string"},
                    barcode: {type:"string"},
                    mortgagedAbleQty:{type:"number"},
                    onhandQty:{type:"number"},
                    pickedQty:{type:"number"},
                    workingQty:{type:"number"},
                    mortgagedQty:{type:"number"},
                    costPrice:{type:"number"},
                    idHold:{type:"boolean"}
                }
            }
        };
    });
