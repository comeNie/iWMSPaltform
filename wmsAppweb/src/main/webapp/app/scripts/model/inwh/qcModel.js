define(['kendo'],
    function (kendo) {
        'use strict';
        return {
            qcHeader:{
                id:"id",
                fields:{
                    id:{type: "number",editable:false,nullable:false},
                    tenantId:{type: "number",editable:false,nullable:false},
                    warehouseId:{type: "number",editable:false,nullable:false},
                    asnId:{type:"number"},
                    totalQty:{type:"number"},
                    totalCategoryQty:{type:"number"},
                    qcPrincipalUser:{type:"string"},
                    description:{type:"string"},
                    statusCode:{type:"string"},
                    createUser:{type:"string"},
                    createTime:{type:"number"},
                    updateUser:{type:"string"},
                    updateTime:{type:"number"},
                    submitUser:{type:"string"},
                    submitDate:{type:"number"},
                    isDel:{type:"boolean"},
                }
            },

            qcDetail:{
                id:"id",
                fields: {
                    id:{type: "number",editable:false,nullable:false},
                    tenantId:{type: "number",editable:false,nullable:false},
                    warehouseId:{type:"number",editable:false,nullable:false},
                    qcId:{type: "number"},
                    asnDetailId:{type: "number"},
                    skuId:{type: "number"},
                    sku:{type: "string"},
                    skuName:{type: "string"},
                    skuBarcode:{type: "string"},
                    totalQty:{type: "number"},
                    samplingRatio:{type: "number"},
                    qcQty:{type: "number"},
                    checkedQty:{type: "number"},
                    qualifiedQty:{type: "number"},
                    unqualifiedQty:{type: "number"},
                    description:{type: "string"},
                    statusCode:{type: "string"},
                    createUser:{type: "string"},
                    createTime:{type: "number"},
                    updateUser:{type: "string"},
                    updateTime:{type: "number"},
                    isDel:{type: "boolean"},
                    inventoryStatusCode:{type:"string"},
                }
            }
        };
    });
