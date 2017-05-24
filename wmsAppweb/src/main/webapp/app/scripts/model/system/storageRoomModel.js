define(['kendo'],
    function (kendo) {
        'use strict';
        return {
            storageRoomHeader:{
                id:"id",
                fields:{
                    id:{type:"number", editable:false,nullable:false},
                    warehouseId:{type: "number"},
                    roomNo:{type: "string"},
                    typeCode:{type:"string",editable: true},
                    storageRoomType:{type:"string"},
                    isActive:{type:"boolean",defaultValue:true},
                    description:{type:"string"},
                    priority:{type:"number"},
                    isDefault:{type:"boolean"},
                    createUser:{type:"string"},
                    createTime:{type:"string"},
                    updateUser:{type:"string"},
                    updateTime:{type:"string"}
                }
            }
        };
    });