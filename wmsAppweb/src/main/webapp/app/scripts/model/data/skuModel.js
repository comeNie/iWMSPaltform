define(['kendo'],
    function(kendo){
        'use strict';
        return {
            skuHeader:{
                id:"id",
                fields: {
                    id: {type:"number",editable: false, nullable: true},
                    cargoOwnerId: {type:"number"},
                    sku: {type:"string"},
                    upc: {type:"string"},
                    barcode: {type:"string"},
                    itemName: {type:"string"},
                    spec: {type:"string"},
                    categorysId: { type:"number"},
                    unitType: {type:"string"},
                    datasourceCode: {type:"string"},
                    producer: {type:"string"},
                    producerCode: {type:"string"},
                    originPlace: {type:"string"},
                    netWeight: {type:"number"},
                    grossWeight: {type:"number"},
                    volume: {type:"number"},
                    warehouseTemp: {type:"number"},
                    transportTemp: {type:"number"},
                    costPrice: {type:"number"},
                    isActive: { type: "boolean"},
                    description: {type:"string"},
                    price1: { type:"number"},
                    price2: { type:"number"},
                    price3: { type:"number"},
                    isDel: { type: "boolean"},
                    createUser: {type:"string"},
                    createTime: { type: "number"},
                    updateUser: {type:"string"},
                    updateTime:{ type: "number"},
                    cargoOwnerBarcode:{ type: "string"}
                }
            }
        };
    });