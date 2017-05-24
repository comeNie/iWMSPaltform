define(['kendo'],
    function(kendo){
        'use strict';
        return {
            cargoOwnerHeader:{
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    tenantId:{type:"number"},
                    cargoOwnerNo:{type:"string"},
                    isActive:{type:"boolean"},
                    shortName:{type:"string"},
                    fullName:{type:"string"},
                    typeCode:{type:"string"},
                    country:{type:"string"},
                    province:{type:"string"},
                    city:{type:"string"},
                    area:{type:"string"},
                    zip:{type:"string"},
                    address:{type:"string"},
                    contact:{type:"string"},
                    telephone:{type:"string"},
                    fax:{type:"string"},
                    email:{type:"string"},
                    description:{type:"string"},
                    isDel:{type:"boolean"},
                    createUser:{type:"string"},
                    createTime:{type:"number"},
                    updateUser:{type:"string"},
                    updateTime:{type:"number"},
                }
            }
        };
    });