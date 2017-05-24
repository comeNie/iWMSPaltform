define(['scripts/controller/controller', '../../model/system/storageRoomModel'], function (controller, storageRoom) {
    "use strict";
    controller.controller('storageRoomController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {

            var storageRoomHeaderUrl = $url.systemStorageRoomUrl,
                storageRoomHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '仓库名称', field: 'warehouseId', align: 'left', width: "130px",template:WMS.UTILS.whFormat},
                    { title: '库房编号', field: 'roomNo', align: 'left', width: "120px"},
                    { title: '库房性质', field: 'typeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('typeCode','RoomType')},
                    { title: '库房类型', field: 'storageRoomType', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('storageRoomType','StorageRoomType')},
                    { title: '是否可用', field: 'isActive', align: 'left', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isActive")},
                    { title: '库房描述', field: 'description', align: 'left', width: "120px"},
                    { title: '优先级', field: 'priority', align: 'left', width: "120px"},
                    { title: '是否默认库房', field: 'isDefault', align: 'left', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isDefault")},
                ],
                storageRoomHeaderDataSource = wmsDataSource({
                    url: storageRoomHeaderUrl,
                    schema: {
                        model:storageRoom.storageRoomHeader
                    }
                });
            storageRoomHeaderColumns = storageRoomHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: storageRoomHeaderDataSource,
                toolbar: [{ template:
                    '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"}],
                columns: storageRoomHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{
                        width:"580"
                    },
                    template: kendo.template($("#storageRoom-kendo-template").html())
                }
            }, $scope);
        }]);
})