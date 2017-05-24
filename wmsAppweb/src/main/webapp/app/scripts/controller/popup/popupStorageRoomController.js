define(['scripts/controller/controller','../../model/system/storageRoomModel'], function(controller,storageRoom) {
    "use strict";
    controller.controller('popupStorageRoomController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var storageRoomUrl = $url.systemStorageRoomUrl,
                selectable = "row",
                storageRoomPopupGridWidgetId = "userPopup",
                storageRoomColumns = [
                    { title: '仓库名称', field: 'warehouseId', align: 'left', width: "130px",template:WMS.UTILS.whFormat},
                    { title: '库房编号', field: 'roomNo', align: 'left', width: "120px"},
                    { title: '库房性质', field: 'typeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('typeCode','RoomType')},
                    { title: '库房类型', field: 'storageRoomType', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('storageRoomType','StorageRoomType')},
                    { title: '是否可用', field: 'isActive', align: 'left', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isActive")},
                ];
            storageRoomColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;
            $scope.selectAllRow = WMS.GRIDUTILS.selectAllRow;
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                widgetId: storageRoomPopupGridWidgetId,
                dataSource: {},
                columns: storageRoomColumns,
                editable: false,
                selectable: "row",
                height:350,
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){
                if (widget.options !== undefined && widget.options.widgetId === storageRoomPopupGridWidgetId) {
                    if (_.isFunction($scope.storageRoomPopup.initParam)) {
                        $scope.storageRoomPopup.initParam($scope);
                        var  warehouseId = 0;
                        if ($scope.selectable) {
                            selectable = $scope.selectable;
                        }
                        if ($scope.url) {
                            storageRoomUrl = $scope.url;
                            widget.setOptions({selectable: selectable,columns:storageRoomColumns});
                        }
                        if ($scope.param) {
                            storageRoomUrl = storageRoomUrl +"/warehouseId/" +$scope.param;
                        }else{
                            kendo.ui.ExtAlertDialog.showError("请先选择仓库!");
                            $scope.closeStorageRoomWindow();
                            return;
                        }
                        var storageRoomDataSource = wmsDataSource({
                            url: storageRoomUrl,
                            schema: {
                                model:storageRoom.storageRoomHeader
                            }
                        });
                        widget.setDataSource(storageRoomDataSource);
                        widget.refresh();
                    }
                }
            });

            $scope.selectStorageRoom = function(){
                // var selectView = $scope.storageRoomPopupGrid.select();
                var selectView = WMS.GRIDUTILS.getCustomSelectedData($scope.storageRoomPopupGrid);
                if (selectable !== "row") {
                    var selectedDataArray = _.map(selectView, function(view) {
                        return $scope.storageRoomPopupGrid.dataItem(view);
                    });
                    if (_.isFunction($scope.storageRoomPopup.setReturnData)) {
                        $scope.storageRoomPopup.setReturnData(selectedDataArray);
                    }
                } else {
                    var selectedData = $scope.storageRoomPopupGrid.dataItem(selectView);
                    if (_.isFunction($scope.storageRoomPopup.setReturnData)) {
                        $scope.storageRoomPopup.setReturnData(selectView);
                    }
                }

                $scope.closeStorageRoomWindow();
            };

            $scope.closeStorageRoomWindow = function(){
                $scope.query = {};
                $scope.storageRoomPopupGrid.dataSource.data([]);
                $scope.storageRoomPopup.close();
            };

        }]);

});
