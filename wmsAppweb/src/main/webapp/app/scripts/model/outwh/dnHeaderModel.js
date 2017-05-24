define(['kendo'],
    function (kendo) {
        'use strict';
        return{
            dnHeader:{
                id:"id",
                fields:{
                    id: {type:"number", editable: false, nullable: true },
                    tenantId:{type: "number"},
                    warehouseId:{type: "number",editable:false,nullable:false},
                    cargoOwnerId:{type: "number"},
                    orderId:{type: "number"},
                    invoiceId:{type: "number"},
                    datasourceCode:{type: "string"},
                    fromtypeCode:{type: "string"},
                    statusCode:{type: "string"},
                    typeCode:{type: "string"},
                    isCancelled:{type: "boolean" ,defaultValue:false},
                    isFailed:{type: "boolean" ,defaultValue:false},
                    carrierId: { type: "number" },
                    carrierName: { type: "string" },
                    auditedTime:{type: "number"},
                    auditedUser: { type: "string" },
                    createUser: { type: "string" },
                    createTime:{type: "number"},
                    updateUser: { type: "string" },
                    updateTime:{type: "number"},
                    isDel:{type: "boolean" ,defaultValue:false},
                    isUrgent:{type:"boolean",defaultValue:false},
                    isPrintsku:{type:"boolean",defaultValue:false},
                    invoiceTypeCode:{type:"string"},
                }
            },

            dnDetail: {
                id: "id",
                fields: {
                    id: {type: "number", editable: false, nullable: false},
                    tenantId: {type: "number"},
                    dnId: {type: "number"},
                    referLineNo: {type: "string"},
                    skuId: {type: "number"},
                    sku: {type: "string"},
                    skuName: {type: "string"},
                    skuBarcode: {type: "string"},
                    inventoryStatusCode: {type: "string"},
                    itemName: {type: "string"},
                    isGroup: {type: "boolean", defaultValue: false},
                    groupNo: {type: "string"},
                    groupName: {type: "string"},
                    qty: {type: "number",defaultValue:1},
                    price: {type: "number"},
                    amount: {type: "number"},
                    payment: {type: "number"},
                    discountFee: {type: "number"},
                    adjustFee: {type: "number"},
                    isGift: {type: "boolean", defaultValue: false},
                    createUser: {type: "string"},
                    createTime: {type: "number"},
                    updateUser: {type: "string"},
                    updateTime: {type: "number"},
                    isCanceled: {type: "boolean", defaultValue: false},
                    isDel: {type: "boolean", defaultValue: false},
                    storageRoomId:{type: "string"},
                }
            }

        };
    });