
define(['kendo'],
    function(kendo){
        'use strict';
        return {

            stocktakingHeader:{
                id:"id",
                fields:{
                    id:{type:"number",editable:false,nullable:true},
                    tenantId:{type:"number"},
                    warehouseId: {type: "number", editable: false, nullable: false},
                    cargoOwnerId: {type: "number"},
                    storageRoomId:{type:"number"},
                    typeCode: {type: "String"},
                    stocktakingTime:{type:"String"},
                    isAutoAdjust:{type: "number", defaultValue: true},
                    statusCode: {type: "String"},
                    totalCategoryQty: {type: "number"},
                    totalLocationQty: {type: "number"},
                    totalStorageRoomQty: {type: "number"},
                    description: {type: "String"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"},
                    submitUser: {type: "String"},
                    submitTime: {type: "String"},
                    isDel: {type: "number", defaultValue: false}
                }
            },
            stoktakingDeatil:{
                id:"id",
                fields:{
                    id:{type: "number",editable:false,nullable:false},
                    adjustId:{type:"number"},
                    tenantId:{type:"number"},
                    headerId:{type:"number"},
                    zoneId:{type:"number"},
                    locationId:{type:"number"},
                    storageRoomId:{type:"number"},
                    palletNo: {type: "String"},
                    cartonNo: {type: "String"},
                    skuId: {type: "number"},
                    skuName: {type: "String"},
                    skuBarcode: {type: "String"},
                    systemQty: {type: "number"},
                    countQty: {type: "number"},
                    recountQty: {type: "number"},
                    isTacked: {type: "boolean"},
                    counter: {type: "number"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"},
                    isDel: {type: "boolean"}
                }
            }


        };
    });