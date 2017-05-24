define(['scripts/controller/controller', '../../model/outwh/waveModel'], function (controller, wave) {
    "use strict";
    controller.controller('waveController',['$scope', '$rootScope', 'wmsLog','allocatedLog','$filter', 'sync', 'url', 'wmsDataSource','wmsReportPrint',
        function($scope, $rootScope, wmsLog, allocatedLog, $filter, $sync, $url, wmsDataSource,wmsReportPrint) {


            var waveUrl = $url.outwhWaveUrl,
                waveColumns = [
                    //WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '波次单号', field: 'id', align: 'left', width: "100px"},
                    //{ title: '数据来源', field: 'datasourceCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('datasourceCode','DataSource')},
                    //{ title: '订单来源', field: 'fromtypeCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('fromtypeCode','ShipmentFrom')},
                    //{ title: '货主', field: 'cargoOwnerId', align: 'left', width: "180px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    //{ title: '波次状态', field: 'statusCode', align: 'left', width: "100px"},
                    //{ title: '承运商', field: 'expressName', align: 'left', width: "100px"},
                    //{ title: '拣货人', field: 'picker', align: 'left', width: "100px"},
                    //{ title: '发货状态', filterable: false, field: 'deliveryStatuscode', template: WMS.UTILS.codeFormat("deliveryStatuscode", "OrderOperationStatus"), align: 'left', width: "120px"},
                    //{ title: '优先发货', field: 'isUrgent', filterable: false, template: WMS.UTILS.checkboxDisabledTmp("isUrgent"), align: 'left', width: "120px"},
                    //{ title: '商品种数', field: 'totalCategoryQty', align: 'left', width: "100px"},
                    { title: '波次订单数量', field: 'totalQty', align: 'left', width: "100px"},
                ],
                shipmentHeaderColumns = [
                   //WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '出库单号', field: 'id', align: 'left', width: "100px"},
                    { title: '通知单号', field: 'dnId', align: 'left', width: "100px"},
                    { title: '数据来源', field: 'datasourceCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('datasourceCode','DataSource')},
                    { title: '订单来源', field: 'fromtypeCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('fromtypeCode','ShipmentFrom')},
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "180px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '分配状态', field: 'allocateStatuscode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('allocateStatuscode','OrderOperationStatus')},
                    { title: '承运商', field: 'expressName', align: 'left', width: "100px"},
                    { title: '运单号', field: 'expressNo', align: 'left', width: "100px"},
                    { title: '车号', field: 'vehicleNo', align: 'left', width: "100px"},
                    { title: '运输方式', field: 'expressTypeCode', align: 'left', width: "100px"},
                    { title: '拣货人', field: 'picker', align: 'left', width: "100px"},
                    { title: '发货状态', filterable: false, field: 'deliveryStatuscode', template: WMS.UTILS.codeFormat("deliveryStatuscode", "OrderOperationStatus"), align: 'left', width: "120px"},
                    { title: '优先发货', field: 'isUrgent', filterable: false, template: WMS.UTILS.checkboxDisabledTmp("isUrgent"), align: 'left', width: "120px"},
                    { title: '实际发货时间', field: 'deliveryTime',filterable: false,  template: WMS.UTILS.timestampFormat("deliveryTime", "yyyy-MM-dd HH:mm:ss"), align: 'left', width: "150px"},
                    { title: '商品种数', field: 'totalCategoryQty', align: 'left', width: "100px"},
                    { title: '总数量', field: 'totalQty', align: 'left', width: "100px"},
                    { title: '总净重', field: 'totalNetweight', align: 'left', width: "100px"},
                    { title: '总毛重', field: 'totalGrossweight', align: 'left', width: "100px"},
                    { title: '总体积', field: 'totalVolume', align: 'left', width: "100px"},
                    { title: '备注', field: 'remark', align: 'left', width: "100px", template: function (dataItem) {
                        return WMS.UTILS.tooLongContentFormat(dataItem, 'remark');
                    }}
                ],
                waveDataSource = wmsDataSource({
                    url: waveUrl,
                    schema: {
                        model: wave.wave
                    }
                });

            waveColumns = waveColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            waveColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

            shipmentHeaderColumns = shipmentHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;
            $scope.selectAllRow = WMS.GRIDUTILS.selectAllRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: waveDataSource,
                toolbar: [
                    { name: "printPicking", template:'<a class="k-button k-button-printPicking k-grid-edit" ng-click="printPicking()">打印拣货单</a>', className:'btn-auth-printPicking'},
                    { name: "printShipment",template:'<a class="k-button k-button-printShipment k-grid-edit" ng-click="printShipment()">打印发货单</a>', className:'btn-auth-printShipment'},
                ],
                columns: waveColumns,
                editable: {
                    mode: "popup",
                    window:{width:"700"},
                    template: kendo.template($("#wave-kendo-template").html())
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
                customChange:function (grid) {
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.waveGrid);

                },
            }, $scope);

            $scope.detailGridOptions = function (dataItem) {
                return WMS.GRIDUTILS.getGridOptions({
                    dataSource: wmsDataSource({
                        url: waveUrl + "/" + dataItem.id+ "/detail",
                        schema: {
                            model: wave.shipmentHeader
                        },
                        otherData: {"waveId": dataItem.id}
                    }),
                    columns: shipmentHeaderColumns,
                    widgetId:"detail",
                    editable: {
                        mode: "popup",
                        window: {
                            width: "600px"
                        },
                        template: kendo.template($("#shipmentHeader-kendo-template").html())
                    },
                    dataBound: function (e) {
                        var grid = this,
                            trs = grid.tbody.find(">tr");
                        if (dataItem.statusCode !== "Initial") {
                            grid.element.find(".k-grid-add").remove();
                            _.each(trs, function (tr, i) {
                                $(tr).find(".k-button").remove();
                            });
                        }
                    },

                },$scope);
            };
            $scope.$on("kendoWidgetCreated", function (event, widget) {

                function getCurrentIds() {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.waveGrid);
                    var ids = [];
                    selectedData.forEach(function (data) {
                        ids.push(data.id);
                    });
                    var id = ids.join(",");
                    return id;
                }

                //出库单"打印"操作
                $scope.printShipment = function () {
                    $(".k-grid-printShipment").attr("disabled", "disabled");
                    var ids = getCurrentIds();
                    if(!ids){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    var url = waveUrl + "/printShipment/"+ids;
                    $sync( url, "GET")
                        .then(function (xhr) {
                            var xhr = xhr.result;
                            var printLog = wmsReportPrint.printShipment(xhr,1);
                            if(printLog){
                                $(".k-grid-printShipment").removeAttr("disabled");
                            }
                        }, function (xhr) {
                            kendo.ui.ExtAlertDialog.showError(xhr);
                        })
                };

            //拣货单"打印"操作
            $scope.printPicking = function () {
                $(".k-grid-printPicking").attr("disabled", "disabled");
                var ids = getCurrentIds();
                if(!ids){
                    kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                    return;
                }
                var url = waveUrl + "/printShipment/"+ids;
                $sync( url, "GET")
                    .then(function (xhr) {
                        var xhr = xhr.result;
                        var printLog = wmsReportPrint.printPicking(xhr,1);
                        if(printLog){
                            $(".k-grid-printPicking").removeAttr("disabled");
                        }
                    }, function (xhr) {
                        kendo.ui.ExtAlertDialog.showError(xhr);
                    })
            };

            //承运商选择
            $scope.windowOpenCarrier =function(parentGrid){
                var typeCode =  "Carrier";
                $scope.organizationsPopup.initParam = function(subScope){
                    subScope.param = typeCode;
                };
                $scope.organizationsPopup.refresh().open().center();
                $scope.organizationsPopup.setReturnData =function (resultData) {
                    if(_.isEmpty(resultData)){
                        return;
                    }
                    //赋值
                    $("#expressName").val(resultData.name);
                }
            }
            })
        }]);
})