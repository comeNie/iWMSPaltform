define(['kendo'],
    function (kendo) {
        'use strict';

        return {
            header: {
                id: "id",
                fields: {
                    id: {type: "number", editable: false, nullable: true },
                    tenantId: { type: 'number' },
                    cargoOwnerId: { type: 'number' },
                    warehouseId: { type: 'number' },
                    orderNo: { type: 'number' },
                    ordertypeCode: {type: 'string'},
                    orderReferno: { type: "string" },
                    operationCode: { type: "string" },
                    statusCode: { type: "string" },
                    description: {type: "string"},
                    createUser: { type: "string" },
                    createTime: { type: "string" }
                }
            }
        };
    }
)