define(['kendo'],
    function(kendo){
        'use strict';
        return {
            organizationsHeader:{
                id:"id",
                fields: {
                    id: {type: "number", editable: false, nullable: true},
                    parentId: {type: "number"},
                    name: {type: "string"},
                    typeCode: {type: "string", editable: true},
                    isActive: {type: "boolean", defaultValue: true},
                    description: {type: "string"},
                    telephone: {type: "string"},
                    contacts: {type: "string"},
                    address: {type: "string"},
                    createUser: {type: "string"},
                    createTime: {type: "number"},
                    updateUser: {type: "string"},
                    updateTime: {type: "number"}
                }
            }
        };
    });
