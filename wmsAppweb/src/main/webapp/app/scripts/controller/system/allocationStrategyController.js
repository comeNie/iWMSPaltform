define(['../controller', '../../model/system/strategyModel'], function (controller, strategy) {
    "use strict";
    controller.controller('allocationStrategyController', ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function ($scope, $rootScope, $sync, $url, wmsDataSource) {
            var allocationStrategyUrl = $url.systemAllocationStrategyUrl,
                allocationStrategyColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '策略名称', field: 'strategyName', align: 'left', width: "120px"},
                    { title: ' 仓库名称', field: 'warehouseId', align: 'left', width: "120px",template:WMS.UTILS.whFormat},
                    { title: ' 库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomStringFormat("storageRoomId")},
                    { title: '排序字段', field: 'orderFieldCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('orderFieldCode','AllocateField')},
                    { title: '排序方向', field: 'directionCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('directionCode','DirectionCode')},
                    { title: '描述', field: 'description', align: 'left', width: "120px"},
                    { title: '是否激活', field: 'isActive', align: 'left', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isActive")},
                    { title: '是否默认', field: 'isDefault', align: 'left', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isDefault")},
                    // {title:'整库优先',field:'isWholePriority',align: 'left',width:"120px",template:WMS.UTILS.checkboxDisabledTmp("isWholePriority")}
                ],
                allocationStrategyDataSource = wmsDataSource({
                    url: allocationStrategyUrl,
                    schema: {
                        model: strategy.strategyModel
                    }
                });
            allocationStrategyColumns = allocationStrategyColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: allocationStrategyDataSource,
                toolbar: [
                    { template: '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"}
                ],
                columns: allocationStrategyColumns,
                widgetId: "allocationStrategy",
                editable: {
                    mode: "popup",
                    window: {
                        width: "480"
                    },
                    template: kendo.template($("#allocationStrategy-kendo-template").html())
                }
            }, $scope);


            $scope.$on("kendoWidgetCreated", function (event,widget) {

                if (widget.options !== undefined && widget.options.widgetId === 'allocationStrategy') {
                    $scope.allocationStrategyGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.allocationStrategy = e.model;
                    });}

                //库房选择
                $scope.windowOpenStorageRoom = function (parentGrid) {
                    var warehouseId = $('#warehouseId').val();
                    if(warehouseId == 0 || warehouseId == null || warehouseId ==undefined){
                        kendo.ui.ExtAlertDialog.showError("请先选择仓库!");
                        return;
                    }
                    $scope.storageRoomPopup.initParam = function (subScope) {
                        subScope.param = warehouseId;
                    };
                    $scope.storageRoomPopup.refresh().open().center();
                    $scope.storageRoomPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        var ids = [];
                        for(var i = 0 ; i < resultData.length ; i ++){
                            ids.push(resultData[i].id);
                        }
                        //赋值
                        $scope.allocationStrategy.set("storageRoomId", ids.toString());
                    }
                }
                //清除库房
                $scope.cleanStorageRoom = function () {
                    $scope.allocationStrategy.set("storageRoomId", '');
                };
            })
        }])


})
