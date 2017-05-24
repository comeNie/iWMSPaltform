define(['kendo'],
       function (kendo) {
           'use strict';
           return{
               skuCategoryHeader:{
                   id:"id",
                fields:{
                    id: {type:"number", editable: false, nullable: true },
                    parentId:{type:"number",  nullable: true},
                    tenantId:{type:"number",  nullable: true},
                    categoryCode:{type: "string",editable: true},
                    categoryName:{type: "string",editable: true},
                    position:{type:"number"},
                    description:{type:"string",editable: true},
                    createUser: { type: "string" },
                    createTime: { type: "string" },
                    updateUser: { type: "string" },
                    updateTime: { type: "string" }
                }
               }
           };
       });

