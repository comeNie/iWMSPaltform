define(['kendo'],
    function(kendo){
        'use strict';
        return {
            strategyModel:{
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true },
                    tenantId:{type:"number"},
                    strategyName:{type:"string"},
                    isActive:{type:"boolean"},
                    isDefault:{type:"boolean"},
                    orderFieldCode:{type:"string"},
                    directionCode:{type:"string"},
                    description:{type:"string"},
                    createUser:{type:"string"},
                    createTime:{type:"number"},
                    updateUser:{type:"string"},
                    updateTime:{type:"number"},
                    warehouseId:{type:"number"},
                    storageRoomId:{type:"string"},
                    isWholePriority:{type:"boolean"},
                }
            }
        };
    });