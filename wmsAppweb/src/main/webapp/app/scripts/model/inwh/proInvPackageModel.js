define(['kendo'],
    function(kendo) {
        'use strict';
        return {
            proInvPackage: {
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    tenantId: {type:"number" },
                    warehouseId: {type:"number" },
                    cargoOwnerId: {type: "number"},
                    workGroupNo:{type:"String"},
                    typeCode:{type:"String"},
                    statusCode: {type: "String"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"}
                }
            },
            proInvPackageDetail:{
                id:"id",
                fields: {
                    id:{type: "number",editable:false,nullable:false},
                    parentId: {type:"number" },
                    tenantId: {type:"number" },
                    warehouseId: {type:"number" },
                    invPackageId: {type:"number" },
                    invPackageQty: {type: "number"},
                    storageRoomId: {type: "number"},
                    spec: {type: "String"},
                    unitType: {type: "String"},
                    typeCode: {type: "String"},
                    createUser: {type: "String"},
                    createTime: {type: "number"},
                    updateUser: {type: "String"},
                    updateTime: {type: "number"},
                }
            }
        };
    });