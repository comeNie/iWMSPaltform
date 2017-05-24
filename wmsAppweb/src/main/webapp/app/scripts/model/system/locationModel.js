define(['kendo'],
    function(kendo){
        'use strict';
        return {
            locationtHeader:{
                id:"id",
                fields: {
                    id: {type:"number", editable: false, nullable: true },
                    warehouseId:{type:"number",  nullable: true },
                    zoneId:{type:"number",  nullable: true },
                    locationNo: {type:"string"},
                    description: {type:"string",editable: true},
                    isActive:{type: "boolean" ,defaultValue:true},
                    typeCode: { type: "string",editable: true},
                    classCode:{type: "string",editable: true},
                    handlingCode:{type: "string",editable: true},
                    categoryCode:{type: "string",editable: true},
                    abcCode:{type: "string",editable: true},
                    isMultisku:{type: "boolean" ,defaultValue:true},
                    isMultilot:{type: "boolean" ,defaultValue:true},
                    putawaySeq:{type:"number"},
                    pickupSeq:{type:"number"},
                    volume:{type:"number"},
                    length:{type:"number"},
                    width:{type:"number"},
                    height:{type:"number"},
                    weightcapacity:{type:"number"},
                    isDefault:{type: "boolean" ,defaultValue:true},
                    createUser: { type: "string" },
                    createTime: { type: "string" },
                    updateUser: { type: "string" },
                    updateTime: { type: "string" },
                    isDel:{type: "boolean" ,defaultValue:true},
                    isUsed:{type: "boolean" ,defaultValue:true}
                }
            }
        };
    });
