define(['kendo'],
    function(kendo){
        'use strict';
        return {
            frozenHeader:{
                id:"id",
                fields: {
                    id: {type: "number", editable: false, nullable: true},
                    tenantId: {type: "number"},
                    warehouseId: {type: "number", editable: false, nullable: false},
                    cargoOwnerId: {type: "number"},
                    name: {type: "string"},
                    factoringType: {type: "string"},
                    statusCode: {type: "string"},
                    totalQty: {type: "number"},
                    totalCartonQty: {type: "number"},
                    totalAmount: {type: "number"},
                    totalNetWeight: {type: "number"},
                    totalGrossWeight: {type: "number"},
                    totalVolume: {type: "number"},
                    factoringOrganizeId: {type: "number"},
                    factoringTime: {type: "string"},
                    factoringStartTime:{type:"string"},
                    factoringEndTime:{type:"string"},
                    auditedOrganizeId: {type: "number"},
                    auditedUser: {type: "string"},
                    auditedTime: {type: "string"},
                    isAudited: {type: "boolean"},
                    isInvalided: {type: "boolean"},
                    createUser: {type: "string"},
                    createTime: {type: "string"},
                    updateUser: {type: "string"},
                    updateTime: {type: "string"},
                    isCancelled: {type: "boolean"},
                    isDel: {type: "boolean"},
                    description: {type: "string"}
                }
            },


            frozenDetail:{
                id:"id",
                fields: {
                    id:{type: "number",editable:false,nullable:false},
                    frozenId:{type: "number"},
                    tenantId:{type: "number"},
                    warehouseId:{type: "number"},
                    skuId:{type: "number"},
                    sku:{type: "string"},
                    skuName:{type: "string"},
                    skuBarcode:{type: "string"},
                    workGroupNo:{type: "number"},
                    locationId:{type: "number"},
                    storageRoomId:{type: "number"},
                    palletNo:{type: "string"},
                    containerNo:{type: "string"},
                    factoringPrice:{type: "number"},
                    factoringQty:{type: "number"},
                    netWeight:{type: "number"},
                    grossWeight:{type: "number"},
                    volume:{type: "number"},
                    description:{type: "string"},
                    costPrice:{type: "number"},
                    totalPrice:{type: "number"},
                    categorysId:{type: "number"},
                    spec:{type: "string"},
                    statusCode: {type: "string"},
                    unitType:{type: "string"},
                    createUser: { type: "string" },
                    createTime:{type: "string"},
                    updateUser: { type: "string" },
                    updateTime:{type: "string"}
                }
            }
        };
    });

