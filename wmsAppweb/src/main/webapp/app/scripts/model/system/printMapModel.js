
define(['kendo'],
    function(kendo){
        'use strict';
        return {
            printMapHeader:{
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    code: {type:"string"},
                    name: { type: "string"},
                    field: {type:"string"},
                    detail: {type:"string"},
                    hasDetail: {type:"boolean",defaultValue: true},
                    createUser: {type:"string"},
                    createTime: { type: "number"},
                    updateUser: {type:"string"},
                    updateTime:{ type: "number"}
                }
            }
        };
    });