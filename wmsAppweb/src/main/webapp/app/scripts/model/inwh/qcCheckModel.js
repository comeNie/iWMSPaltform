define(['kendo'],
    function (kendo) {
        'use strict';
        return {
            qcCheck: {
                id: "id",
                fields: {
                    id: {type: "number", editable: false, nullable: false},
                    tenantId: {type: "number"},
                    qcId: {type: "number"},
                    warehouseId: {type: "number"},
                    qcDetailId: {type: "number"},
                    isQualified: {type: "boolean"},
                    unQualifiedReason: {type: "string"},
                    statusCode: {type: "string"},
                    typeCode: {type: "string"},
                    createUser: {type: "string"},
                    createTime: {type: "number"},
                    updateUser: {type: "string"},
                    updateTime: {type: "number"},
                    isDel: {type: "boolean"},
                }
            },
            qcCheckdetail: {
                id: "id",
                fields: {
                    id: {type: "number", editable: false, nullable: false},
                    tenantId: {type: "number"},
                    qcId: {type: "number"},
                    warehouseId: {type: "number"},
                    qcDetailId: {type: "number"},
                    skuId: {type: "number"},
                    qcQty: {type: "number"},
                    isQualified: {type: "boolean"},
                    unQualifiedReason: {type: "string"},
                    description: {type: "string"},
                    statusCode: {type: "string"},
                    createUser: {type: "string"},
                    createTime: {type: "number"},
                    updateUser: {type: "string"},
                    updateTime: {type: "number"},
                    isDel: {type: "boolean"},
                }
            }
        };
    });
