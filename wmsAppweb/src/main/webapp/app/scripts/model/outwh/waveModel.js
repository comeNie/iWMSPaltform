define(['kendo'],
    function(kendo){
        'use strict';
        return {
            wave:{
                id:"id",
                fields: {
                    id: {type:"number", editable: false, nullable: true },
                    tenantId:{type: "number"},
                    warehouseId:{type: "number",editable:false,nullable:false},
                    cargoOwnerId:{type: "number"},
                    datasourceCode:{type: "string"},
                    fromtypeCode:{type: "string"},
                    promotionName:{type: "string"},
                    platformCode:{type: "string"},
                    typeCode:{type: "string"},
                    orderStructCode:{type: "string"},
                    invoiceoptionCode:{type: "string"},
                    expressName:{type: "string"},
                    distributorNo:{type: "string"},
                    totalOrderQty:{type: "number"},
                    totalCategoryQty:{type: "number"},
                    expressTypeCode:{type: "string"},
                    picker:{type: "string"},
                    containerNo:{type: "string"},
                    statusCode:{type: "string"},
                    totalQty:{type: "number"},
                    isCod:{type: "boolean" ,editable: true, nullable: true},
                    isPrintExpress:{type: "boolean" ,editable: true, nullable: true},
                    isPrintDelivery:{type: "boolean" ,editable: true, nullable: true},
                    isPrintPicking:{type: "boolean" ,editable: true, nullable: true},
                    isPrintInvoice:{type: "boolean" ,editable: true, nullable: true},
                    printExpressUser:{type: "string"},
                    printExpressTime:{type: "string"},
                    createUser: { type: "string" },
                    createTime: { type: "string" },
                    updateUser: { type: "string" },
                    updateTime: { type: "string" },
                    isDel: {type:"boolean",defaultValue:false},

                }
            }
        };
    });