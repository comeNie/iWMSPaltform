define(['kendo'],
    function(kendo){
        'use strict';
        return {
            adjustmentHeader:{
                id:"id",
                fields:{
                    id:{type:"number",editable:false,nullable:true},
                    tenantId:{type:"number"},
                    warehouseId: {type: "number", editable: false, nullable: false},
                    cargoOwnerId: {type: "number"},
                    referNo: {type: "String"},
                    resonCode: {type: "String"},
                    statusCode: {type: "String"},
                    description: {type: "String"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"},
                    auditedUser: {type: "String"},
                    auditedTime: {type: "number"},
                    isDel: {type: "boolean"}
                }
            },
            adjustmentDetail:{
                id:"id",
                fields:{
                    id:{type: "number",editable:false,nullable:false},
                    adjustId: {type: "number"},
                    tenantId:{type:"number"},
                    referDetailNo: {type: "String"},
                    zoneId:{type:"number"},
                    locationId:{type:"number"},
                    storageRoomId:{type:"number"},
                    palletNo: {type: "String"},
                    containerNo: {type: "String"},
                    skuId:{type:"number"},
                    sku: {type: "String"},
                    barcode: {type: "String"},
                    skuName: {type: "String"},
                    adjustedBeforeQty:{type:"number"},
                    adjustedAfterQty:{type:"number"},
                    adjustedQty:{type:"number"},
                    adjustedBeforePrice:{type:"number"},
                    adjustedAfterPrice:{type:"number"},
                    beforeStatusCode:{type:"String"},
                    afterStatusCode:{type:"String"},
                    description:{type:"String"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"},
                    statusCode: {type: "String"},
                    isDel: {type: "boolean"}
                }
            }
    };
});
