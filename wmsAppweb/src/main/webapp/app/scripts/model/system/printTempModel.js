
define(['kendo'],
    function(kendo){
        'use strict';
        return {
            printTempHeader:{
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    reportCategoryCode: {type:"string"},
                    reportName: { type: "string"},
                    content: {type:"String"},
                    carrier: {type:"string"},
                    printOptions: {type:"String"},
                    projectCode: {type:"string"},
                    isDefault: {type:"boolean",defaultValue: true},
                    createUser: {type:"string"},
                    createTime: { type: "number"},
                    updateUser: {type:"string"},
                    updateTime:{ type: "number"}
                }
            }
        };
    });