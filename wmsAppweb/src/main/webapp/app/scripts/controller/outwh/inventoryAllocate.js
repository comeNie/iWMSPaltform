define(['app', 'kendo', 'scripts/common/sync','scripts/common/wmsDataSource'],function(app, kendo){
    'use strict';
    app.factory('allocatedLog', ['$http', '$q', 'sync','url','wmsDataSource', function ($http, $q, $sync,$url, wmsDataSource) {
       var AllocatedUtil = function(){
           this.allocateLog = function(dataItem) {
               return WMS.GRIDUTILS.getGridOptions({
                   dataSource: wmsDataSource({
                       url: $url.outwhShipmentHeaderUrl + "/" + dataItem.id + "/allocateResult",
                       schema: {
                           model: {
                               id: "id",
                               fields: {
                                   id: {type: "number", editable: false, nullable: true }
                               }
                           },
                           total: function (total) {
                               return total.length > 0 ? total[0].total : 0;
                           }
                       }
                   }),
                   columns: [
                       { title: '分配单号', field: 'id', align: 'left', width: "120px"},
                       //{ title: '分配库房', field: 'storageRoomId', align: 'left', width: "120px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                       { title: '分配库房', field: 'storageRoomName', align: 'left', width: "120px"},
                       { title: '商品', field: 'skuItemName', align: 'left', width: "120px"},
                       { title: 'SKU', field: 'sku', align: 'left', width: "120px"},
                       { title: '商品条码', field: 'skuBarCode', align: 'left', width: "120px"},
                       { title: '分配数量', field: 'allocatedQty', align: 'left', width: "120px"},
                       // { title: '拣货数量', field: 'pickedQty', align: 'left', width: "120px"},
                       { title: '创建人', field: 'createUser', align: 'left', width: "120px"},
                       { title: '创建时间', field: 'createTime', align: 'left', width: "120px", template: WMS.UTILS.timestampFormat("createTime")}
                   ]
               });
           };
       };
        return new AllocatedUtil();
    }]);
});