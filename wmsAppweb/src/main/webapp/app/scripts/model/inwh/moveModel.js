define(['kendo'],
    function(kendo){
        'use strict';
        return {
            move:{
                id:"id",
                fields:{
                    id:{type:"number",editable:false,nullable:true},
                    tenantId:{type:"number"},
                    warehouseId: {type: "number", editable: false, nullable: false},
                    cargoOwnerId: {type: "number"},
                    moveReason: {type: "String"},
                    datasourceCode: {type: "String"},
                    statusCode: {type: "String"},
                    skuId: {type: "number"},
                    sku: {type: "String"},
                    barcode: {type: "String"},
                    skuName: {type: "String"},
                    fromRoomId: {type: "number"},
                    toRoomId: {type: "number"},
                    fromZoneId: {type: "number"},
                    toZoneId: {type: "number"},
                    fromLocationId: {type: "number"},
                    toLocationId: {type: "number"},
                    fromPalletNo: {type: "String"},
                    toPalletNo: {type: "String"},
                    fromContainerNo: {type: "String"},
                    toContainerNo: {type: "String"},
                    movedQty: {type: "number"},
                    description: {type: "String"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"},
                    submitUser: {type: "String"},
                    submitTime: {type: "number"},
                    referNo: {type: "String"},
                    isDel: {type: "boolean", defaultValue: false}
                }
            }


        };
    });