define(['kendo'],
    function(kendo){
        'use strict';
        return {
            customerHeader:{
                id:"id",
                fields: {
                    id: {type:"number", editable: false, nullable: true },
                    tenantId:{type:"number"},
                    customerName:{type:"string"},
                    organizationId:{type:"number"},
                    typeCode:{type:"string"},
                    isActive:{type: "boolean" ,defaultValue:true},
                    description:{type:"string"},
                    telephone:{type:"string"},
                    contacts:{type:"string"},
                    address:{type:"string"},
                    createUser:{type:"string"},
                    createTime:{type:"number"},
                    updateUser:{type:"string"},
                    updateTime:{type:"number"},
                }
            }
        };
    });

