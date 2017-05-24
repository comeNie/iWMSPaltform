define(['scripts/controller/controller', '../../model/inwh/qcModel'], function (controller, qc) {
    "use strict";
    controller.controller('qcController',['$scope', '$rootScope','wmsLog', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope , wmsLog , $sync, $url, wmsDataSource) {
            var qcUrl = $url.inwhQcHeaderUrl,
                qcHeaderColumns = [

                    { title: '收货单号', field: 'id', align: 'left', width: "120px"},
                    { title: '通知单号', field: 'asnId', align: 'left', width: "120px"},
                    { title: '总数量', field: 'totalQty', align: 'left', width: "120px"},
                    { title: '总品种数', field: 'totalCategoryQty', align: 'left', width: "120px"},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '收货人', field: 'qcPrincipalUser', align: 'left', width: "120px"},
                    { title: '备注', field: 'description', align: 'left', width: "120px"},
                ],
                qcDetailColumns = [
                    { title: '明细单号', field: 'id', align: 'left', width: "120px"},
                    { title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                    { title: '商品编码', field: 'sku', align: 'left', width: "120px"},
                    { title: '货主商品条码', field: 'skuBarcode', align: 'left', width: "120px"},
                    { title: '总数量', field: 'totalQty', align: 'left', width: "120px"},
                    { title: '检收数量', field: 'qcQty', align: 'left', width: "120px"},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('statusCode','ReceiptStatus')},
                    { title: '库房', field: 'storageRoomId', align: 'left', width: "120px",template:WMS.UTILS.storageRoomStringFormat("storageRoomId", "storageRoomSrc")},
                    //{ title: '库存状态', field: 'inventoryStatusCode', align: 'left', width: "120px"},
                    { title: '备注', field: 'description', align: 'left', width: "120px"},
                ],
                qcDataSource = wmsDataSource({
                    url: qcUrl,
                    schema: {
                        model:qc.qcHeader
                    }
                });
            qcHeaderColumns=qcHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            qcDetailColumns=qcDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            qcHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: qcDataSource,
                toolbar: [
                    {name:'finish',template: '<a class="k-button k-button-finish" ng-click="finish(dataItem)">收货完成</a>',className:'btn-auth-finish'},
                ],
                columns: qcHeaderColumns,
                widgetId:"header",

                customChange:function (grid) {
                    $(".k-grid-finish").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.qcHeaderGrid);
                    if (selected.length > 1) {
                        kendo.ui.ExtAlertDialog.showError(" 最多只能选择一条数据!");
                        $scope.qcHeaderGrid.dataSource.read();
                        $(".k-button-finish").show();
                        return;
                    }
                    if (selected.length === 1) {
                        var data = selected[0];
                        if (data.statusCode === 'Submitted') {;
                            $(".k-button-finish").show();
                        }
                        if (data.statusCode === 'Finished') {
                            $(".k-button-finish").hide();
                        }
                    }else {
                        $(".k-button-finish").show();
                    }
                }

            }, $scope);



            //操作日志
            $scope.logOptions = wmsLog.operationLog;

            $scope.qcDetailOptions = function (dataItem) {
                var defaultOptions = {
                    dataSource: wmsDataSource({
                        url: qcUrl + "/" + dataItem.id+ "/detail",
                        schema: {
                            model: qc.qcDetail
                        }
                    }),
                    columns: qcDetailColumns,
                    widgetId:"detail",
                    toolbar: [
                        { name: "recipient ", template: '<a class="k-button k-button-recipient" ng-click="qcReceive(dataItem)">收货</a>',
                            className: "btn-auth-recipient"}
                    ],
                    dataBound:function (e) {
                        var grid = this,
                            trs = grid.tbody.find(">tr");
                        if (dataItem.statusCode !== "Submitted") {
                            grid.element.find(".k-button-recipient").remove(); //明细toolbar按钮
                        }
                    },
                }

                return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
            };




            //质检主表显示选项
            $scope.$on("kendoWidgetCreated",function (event,widget) {

                if (widget.options !== undefined && widget.options.widgetId === 'header') {
                    $scope.qcHeaderGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                //质检单完成
                $scope.finish = function () {
                    executeOperationRequest("finish");
                }

                var executeOperationRequest = function (type) {
                    var selectData = WMS.GRIDUTILS.getCustomSelectedData($scope.qcHeaderGrid);
                    if (selectData.length ===0 ) {
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据！");
                        return;
                    }
                    var id =selectData[0].id;
                    var url, method;
                    if ("finish" === type) {
                        url = qcUrl + "/finish/" + id;
                        method ="PUT";
                    }
                    $sync(url, method).then(function (xhr) {
                        $scope.qcHeaderGrid.dataSource.read();
                    },function (xhr) {
                        $scope.qcHeaderGrid.dataSource.read();
                    })
                }


            });



            $scope.$on("kendoWidgetCreated",function (event,widget) {

                if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                    $scope.qcDetailGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                //质检收货
                $scope.qcReceive = function (dataItem) {
                    $scope.qcReceivePopup.refresh().open().center();
                    $scope.qcReceiveModel = {};
                    $scope.qcReceiveModel.qcId = dataItem.id;
                };

                //质检收货确定
                $scope.qcReceiveConfirm = function (e) {
                    var formValidator = $(e.target).parents(".k-edit-form-container").kendoValidator().data("kendoValidator");
                    if (!formValidator.validate()) {
                        return;
                    }
                    var qcId = $scope.qcReceiveModel.qcId;
                    var barcode = $scope.qcReceiveModel.barcode;
                    var qcQty = $scope.qcReceiveModel.qcQty;
                    var storageRoomId =$("#storageRoomId").val();
                    var warehouseTemp = $scope.qcReceiveModel.warehouseTemp;
                    var url = qcUrl+"/qcReceive/"+qcId;
                    var params = {
                        qcId:qcId,
                        barcode:barcode,
                        qcQty:qcQty,
                        storageRoomId:storageRoomId,
                        warehouseTemp:warehouseTemp
                    };
                    $sync(qcUrl+"/qcReceive/"+qcId,"PUT",{data:params})
                        .then(function (xhr) {
                            $scope.qcReceivePopup.close();
                            $scope.qcHeaderGrid.dataSource.read();
                        },function (xhr) {
                            $scope.qcHeaderGrid.dataSource.read();
                            $scope.qcReceivePopup.close();
                        })

                };

                //质检收货弹窗取消
                $scope.qcReceiveClose = function () {
                    $scope.qcReceivePopup.close();
                }

            });



        }]);
})
