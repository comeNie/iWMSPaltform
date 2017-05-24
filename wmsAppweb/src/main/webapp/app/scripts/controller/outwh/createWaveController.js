define(['scripts/controller/controller', '../../model/outwh/shipmentHeaderModel'], function (controller, createWave) {
    "use strict";
    controller.controller('createWaveController',['$scope', '$rootScope', 'wmsLog','allocatedLog','$filter', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope, wmsLog, allocatedLog, $filter, $sync, $url, wmsDataSource) {
            $("#fromtypeCode").kendoMaskedTextBox({
                mask: "(999) 000-0000"
            });
            var createWaveUrl = $url.outwhShipmentHeaderUrl+'/wave',
                createWaveColumns = [
                    { title: '出库单号', field: 'id', align: 'left', width: "100px"},
                    { title: '通知单号', field: 'dnId', align: 'left', width: "100px"},
                    { title: '数据来源', field: 'datasourceCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('datasourceCode','DataSource')},
                    { title: '订单来源', field: 'fromtypeCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('fromtypeCode','ShipmentFrom')},
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "180px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '分配状态', field: 'allocateStatuscode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('allocateStatuscode','OrderOperationStatus')},
                    { title: '承运商', field: 'expressName', align: 'left', width: "100px"},
                    { title: '优先发货', field: 'isUrgent', filterable: false, template: WMS.UTILS.checkboxDisabledTmp("isUrgent"), align: 'left', width: "120px"},
                    { title: '商品种数', field: 'totalCategoryQty', align: 'left', width: "100px"},
                    { title: '总数量', field: 'totalQty', align: 'left', width: "100px"},
                    { title: '总净重', field: 'totalNetweight', align: 'left', width: "100px"},
                    { title: '总毛重', field: 'totalGrossweight', align: 'left', width: "100px"},
                    { title: '总体积', field: 'totalVolume', align: 'left', width: "100px"},
                ],

                createWaveDataSource = wmsDataSource({
                    url: createWaveUrl,
                    schema: {
                        model: createWave.shipmentHeader,
                    }
                });

            createWaveColumns = createWaveColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            createWaveColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;
            $scope.selectAllRow = WMS.GRIDUTILS.selectAllRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: createWaveDataSource,
                toolbar: [//{ name: "create", text: "新增", className:'btn-auth-add'},
                    { name: "submit", text: "生成波次", className:'btn-auth-submit'},
                ],
                columns: createWaveColumns,
                editable: {
                    mode: "popup",
                    window:{width:"700"},
                    template: kendo.template($("#createWave-kendo-template").html())
                },
                widgetId:"header",
                dataBound:function (e) {
                    var grid = this,
                        trs = grid.tbody.find(">tr");
                    _.each(trs, function (tr, i) {
                        var record = grid.dataItem(tr);
                        if (record.statusCode !== 'Initial') {
                            $(tr).find(".k-button").remove();
                        }
                    });
                },

            }, $scope);
            $scope.$on("kendoWidgetCreated", function (event, widget) {

                function getCurrentIds() {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.createWaveGrid);
                    var ids = [];
                    selectedData.forEach(function (data) {
                        ids.push(data.id);
                    });
                    var id = ids.join(",");
                    return id;
                }


                //出库单"提交"操作
                $(".k-grid-submit").on('click', function () {
                    var ids = getCurrentIds();
                    if (!ids) {
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    $sync(createWaveUrl + "/submit/" + ids, "PUT").then(function (xhr) {
                        $scope.createWaveGrid.dataSource.read();
                    }).then(function (xhr) {
                        $scope.createWaveGrid.dataSource.read();
                    })
                });

        });
    }]);
})