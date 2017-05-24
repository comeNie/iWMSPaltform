define(['scripts/controller/controller', '../../model/outwh/shipmentHeaderModel'], function (controller, shipmentHeader) {
    "use strict";
    controller.controller('shipmentHeaderController',['$scope', '$rootScope', 'wmsLog','allocatedLog','$filter', 'sync', 'url', 'wmsDataSource','wmsReportPrint',
        function($scope, $rootScope, wmsLog, allocatedLog, $filter, $sync, $url, wmsDataSource,wmsReportPrint) {

            $scope.order = {};
            $scope.invoice = {};

            $("#fromtypeCode").kendoMaskedTextBox({
                mask: "(999) 000-0000"
            });
            var shipmentHeaderUrl = $url.outwhShipmentHeaderUrl,
                shipmentHeaderColumns = [
                    //WMS.GRIDUTILS.CommonOptionButton(),
                   { title: '出库单号', field: 'id', align: 'left', width: "100px"},
                   { title: '通知单号', field: 'dnId', align: 'left', width: "100px"},
                   { title: '订单号', field: 'orderId', align: 'left', width: "100px"},
                    { title: '数据来源', field: 'datasourceCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('datasourceCode','DataSource')},
                    { title: '订单来源', field: 'fromtypeCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('fromtypeCode','ShipmentFrom')},
                    // { title: '仓库名称',filterable: false, field: 'warehouseId', align: 'left', width: "120px;", template: WMS.UTILS.whFormat},
                    // { title: '库房', field: 'storageRoomId', align: 'left', width: "120px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "180px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '分配状态', field: 'allocateStatuscode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('allocateStatuscode','OrderOperationStatus')},
                    //{ title: '波次单号', field: 'waveId', align: 'left', width: "100px"},
                    //{ title: '波次序号', field: 'waveSeq', align: 'left', width: "100px"},
                    { title: '承运商', field: 'expressName', align: 'left', width: "100px"},
                    { title: '运单号', field: 'expressNo', align: 'left', width: "100px"},
                    { title: '车号', field: 'vehicleNo', align: 'left', width: "100px"},
                    { title: '运输方式', field: 'expressTypeCode', align: 'left', width: "100px"},
                    { title: '拣货人', field: 'picker', align: 'left', width: "100px"},
                    { title: '发货状态', filterable: false, field: 'deliveryStatuscode', template: WMS.UTILS.codeFormat("deliveryStatuscode", "OrderOperationStatus"), align: 'left', width: "120px"},
                    { title: '优先发货', field: 'isUrgent', filterable: false, template: WMS.UTILS.checkboxDisabledTmp("isUrgent"), align: 'left', width: "120px"},
                    { title: '实际发货时间', field: 'deliveryTime',filterable: false,  template: WMS.UTILS.timestampFormat("deliveryTime", "yyyy-MM-dd HH:mm:ss"), align: 'left', width: "150px"},
                    //{ title: '订单日期', field: 'order.orderTime',filterable: false,  template: WMS.UTILS.timestampFormat("order.orderTime", "yyyy-MM-dd HH:mm:ss"), align: 'left', width: "150px"},
                    //{ title: '支付时间', field: 'order.paymentTime', filterable: false, template: WMS.UTILS.timestampFormat("order.paymentTime", "yyyy-MM-dd HH:mm:ss"), align: 'left', width: "150px"},
                    { title: '商品种数', field: 'totalCategoryQty', align: 'left', width: "100px"},
                    { title: '总数量', field: 'totalQty', align: 'left', width: "100px"},
                    { title: '总净重', field: 'totalNetweight', align: 'left', width: "100px"},
                    { title: '总毛重', field: 'totalGrossweight', align: 'left', width: "100px"},
                    { title: '总体积', field: 'totalVolume', align: 'left', width: "100px"},
                    //{ filterable: false, title: '应付金额', field: 'actualPayment', align: 'left', width: "120px"},
                    //{ filterable: false, title: '发票金额', field: 'invoiceTotal', align: 'left', width: "120px"},
                    //{ filterable: false, title: '收货地址', field: 'address', align: 'left', width: "120px", template: function (dataItem) {
                    //    return WMS.UTILS.tooLongContentFormat(dataItem, 'address');
                    //}},
                   //{ title: '已打印物流单', field: 'isPrintExpress',template:WMS.UTILS.checkboxDisabledTmp("isPrintExpress"), align: 'left', width: "130px"},
                    //{ title: '已打印发货单', field: 'isPrintDelivery', template:WMS.UTILS.checkboxDisabledTmp("isPrintDelivery"),align: 'left', width: "130px"},
                    //{ title: '已打印拣货单', field: 'isPrintPicking',template:WMS.UTILS.checkboxDisabledTmp("isPrintPicking"), align: 'left', width: "130px"},
                    //{ title: '已打印发票', field: 'isPrintInvoice', template:WMS.UTILS.checkboxDisabledTmp("isPrintInvoice"),align: 'left', width: "130px"},
                    //{ title: '物流单打印人', field: 'printExpressUser', align: 'left', width: "150px"},
                    //{ title: '物流单打印时间', field: 'printExpressTime', align: 'left', width: "150px"},
                    //{ title: '已取消', field: 'isCancelled',template:WMS.UTILS.checkboxDisabledTmp("isCancelled"), align: 'left', width: "100px"},
                    //{ title: '已上传电子面单', field: 'isEwaybillFinished',template:WMS.UTILS.checkboxDisabledTmp("isEwaybillFinished"),align: 'left', width: "100px"},
                    { title: '备注', field: 'remark', align: 'left', width: "100px", template: function (dataItem) {
                        return WMS.UTILS.tooLongContentFormat(dataItem, 'remark');
                    }}
               ],
               commonOptButton = $.extend(true, {}, { name: "updateStorageRoom", width: "100px", className: "btn-auth-storageRoom", template: "<a class='k-button k-button-custom-command'  name='updateStorageRoom' href='\\#' ng-click='updateStorageRoom(this);'>更新库房</a>"});
              var shipmentDetailColumns= [
                     commonOptButton,
                    { title: ' 明细单号', field: 'id', align: 'left', width: "100px"},
                    { title: '商品编码', field: 'sku', align: 'left', width: "120px"},
                    { title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                    { title: '货主商品条码', field: 'skuBarcode', align: 'left', width: "120px"},
                    { title: '出货库房', field: 'storageRoomId', align: 'left', width: "120px",template:WMS.UTILS.storageRoomStringFormat("storageRoomId", "storageRoomSrc")},
                    { title: ' 期望出库数量', field: 'orderedQty', align: 'left', width: "130px"},
                    { title: ' 已分配数量', field: 'allocatedQty', align: 'left', width: "100px"},
                    //{ title: ' 已拣货数量', field: 'pickedQty', align: 'left', width: "100px"},
                    { title: ' 已出库数量', field: 'shippedQty', align: 'left', width: "100px"},
                    { title: ' 销售订单号', field: 'saleOrderNo', align: 'left', width: "100px"},
                    { title: ' 参考单号', field: 'referNo', align: 'left', width: "100px"},
                    //{ title: ' 毛重', field: 'grossWeight', align: 'left', width: "100px"},
                    //{ title: ' 净重', field: 'netWeight', align: 'left', width: "100px"},
                    //{ title: ' 体积', field: 'volume', align: 'left', width: "100px"},
                    //{ title: ' 金额', field: 'amount', align: 'left', width: "100px"},
                    //{ title: ' 单价', field: 'price', align: 'left', width: "100px"},
                    //{ title: ' 折扣金额', field: 'discountAmount', align: 'left', width: "100px"},
                    //{ title: ' 优惠金额', field: 'promotionAmount', align: 'left', width: "100px"},
                    //{ title: ' 实付金额', field: 'actualPayment', align: 'left', width: "100px"},
                    //{ title: ' 是否赠品', field: 'isGift', align: 'left', width: "100px",template:WMS.UTILS.checkboxDisabledTmp("isGift")},
                    { title: ' 批次号', field: 'workGroupNo', align: 'left', width: "100px"},
                    { title: ' 说明', field: 'description', align: 'left', width: "100px"}
                    ],
                shipmentHeaderDataSource = wmsDataSource({
                    url: shipmentHeaderUrl,
                    schema: {
                        model: shipmentHeader.shipmentHeader
                    },
                    otherData: {"order": $scope.order, "invoice": $scope.invoice}
                });

            shipmentHeaderColumns = shipmentHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            shipmentHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

            shipmentDetailColumns = shipmentDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;
            $scope.selectAllRow = WMS.GRIDUTILS.selectAllRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: shipmentHeaderDataSource,
                toolbar: [//{ name: "create", text: "新增", className:'btn-auth-add'},
                    { name: "submit", template:'<a class="k-button k-button-submit k-grid-edit" ng-click="submit()">提交</a>', className:'btn-auth-submit'},
                    //{ name: "batchDelete", text: "批量删除", className:'btn-auth-batchDelete'},
                    { name: "repealed", template:'<a class="k-button k-button-repealed k-grid-edit" ng-click="repealed()">撤销</a>', className:'btn-auth-repealed'},
                    { name: "printPicking", template:'<a class="k-button k-button-printPicking k-grid-edit" ng-click="printPicking()">打印拣货单</a>', className:'btn-auth-printPicking'},
                    { name: "printShipment", template:'<a class="k-button k-button-printShipment k-grid-edit" ng-click="printShipment()">打印发货单</a>', className:'btn-auth-printShipment'}
                ],
                columns: shipmentHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{width:"700"},
                    template: kendo.template($("#shipmentHeader-kendo-template").html())
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
                    $(".k-button-submit").hide();
                    $(".k-button-repealed").hide();
                    $(".k-button-printPicking").hide();
                    $(".k-button-printShipment").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.shipmentHeaderGrid);
                    if (selected.length > 0) {
                        var submit = 0, packing = 0, del = 0,repealed = 0,print = 0, size = selected.length;
                        $.each(selected, function () {
                            if (this.statusCode === 'Initial') {
                                del++;
                                submit++;
                            }
                            if (this.statusCode === 'Submitted' && this.deliveryStatuscode === 'Finished') {
                                print++;
                                repealed--;
                            }
                            if (this.statusCode === 'Submitted') {
                                repealed++;
                            }
                            if (this.deliveryStatuscode === 'None' && this.allocateStatuscode === 'Finished') {
                                packing++;
                            }
                            if (this.statusCode === 'Initial') {
                                repealed--;
                            }
                        });
                        if (submit === size) {
                            $(".k-button-submit").show();

                        }
                        if (repealed === size) {
                            $(".k-button-repealed").show();

                        }if (packing === size ) {
                            $(".k-button-printPicking").show();
                        }
                        if(print === size){
                            $(".k-button-printShipment").show();
                        }
                    } else {
                        $(".k-button-submit").show();
                        $(".k-button-repealed").show();
                        $(".k-button-printPicking").show();
                        $(".k-button-printShipment").show();
                    }
                },
                edit: function (e) {
                    if (!(e.model.order && e.model.order.orderTime)) {
                        var value = $filter('date')(new Date(), 'yyyy/MM/dd 00:00:00');
                        $scope.order.orderTime = value;
                        $scope.order.paymentTime = value;
                        $("[name='order.orderTime']").val(value);
                        $("[name='order.paymentTime']").val(value);
                    }
                }
            }, $scope);




            $scope.shipmentDetailOptions = function (dataItem) {
                return WMS.GRIDUTILS.getGridOptions({
                    dataSource: wmsDataSource({
                        url: shipmentHeaderUrl + "/" + dataItem.id+ "/detail",
                        schema: {
                            model: shipmentHeader.shipmentDetail
                        },
                        otherData: {"shipmentId": dataItem.id}
                    }),
                    columns: shipmentDetailColumns,
                    widgetId:"detail",
                    editable:false,
                    toolbar: [//{ name: "create", text: "新增", className:'btn-auth-add'}
                        ],
                    save: function (e) {
                        e.model.order = $scope.order;
                        e.model.invoice = $scope.invoice;
                        var skuIdVal = $("#skuId").val();
                        if (skuIdVal === "") {
                            kendo.ui.ExtAlertDialog.showError("请至少选择一个SKU商品信息!");
                            return;
                        }
                    },
                   dataBound: function (e) {
                        var grid = this,
                            trs = grid.tbody.find(">tr");
                        if (dataItem.statusCode !== "Initial") {
                            grid.element.find(".k-button-add").remove();
                            _.each(trs, function (tr, i) {
                                $(tr).find(".k-button").remove();
                            });
                        }
                    },

                },$scope);
            };

            //操作日志
            $scope.logOptions = wmsLog.operationLog;

            //库存分配记录
            $scope.allocateOptios = allocatedLog.allocateLog;


            //基本信息
            $scope.shipmentBaseOptions = function (dataItem) {
                $sync(shipmentHeaderUrl + "/" + dataItem.id+"/detail/basic", "GET")
                    .then(function (xhr) {
                        dataItem.order = xhr.result.order;
                        dataItem.invoice= xhr.result.invoice;
                     });
            };

            var dataSource;
            $sync(window.BASEPATH + "/index/storageRoom/warehouseId",'GET').then(function (data) {
                dataSource = data.result;
            })
            $scope.$on("kendoWidgetCreated", function (event, widget) {

                $scope.selectOptions = {
                    placeholder: "请选择库房",
                    dataTextField: "key",
                    dataValueField: "value",
                    valuePrimitive: true,
                    autoBind: false,
                    dataSource:  dataSource
                };

                if (widget.options !== undefined && widget.options.widgetId === 'header') {
                    widget.bind("edit", function (e) {
                        if (!e.model.order) {
                            e.model.order = {isCod: ""};
                        }
                        if (!e.model.invoice) {
                            e.model.invoice = {invoiceTypeCode: ""};
                        }
                    });
                }

                if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                    $scope.shipmentDetailGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }




                //商品选择
                $scope.windowOpen = function (parentGrid) {
                    var cargoOwnerId = parentGrid.$parent.dataItem.cargoOwnerId;
                    $scope.skuPopup.initParam = function (subScope) {
                        subScope.param = cargoOwnerId;
                    };
                    $scope.skuPopup.refresh().open().center();
                    $scope.skuPopup.setReturnData = function (resultData) {

                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        //赋值
                        $scope.editModel.set("skuId", resultData.id);
                        $scope.editModel.set("sku",resultData.sku);
                        $scope.editModel.set("skuName", resultData.itemName);
                        $scope.editModel.set("skuBarcode", resultData.barcode);
                        $scope.editModel.set("spec",resultData.spec);
                        $scope.editModel.set("unitType",resultData.unitType);
                        $scope.editModel.set("netWeight",resultData.netWeight);
                        $scope.editModel.set("grossWeight",resultData.grossWeight);
                        $scope.editModel.set("volume",resultData.volume);
                        $scope.editModel.set("warehouseTemp",resultData.warehouseTemp);
                        $scope.editModel.set("transportTemp",resultData.transportTemp);
                        $scope.editModel.set("costPrice",resultData.costPrice);
                    };
                }


                function getCurrentIds() {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.shipmentHeaderGrid);
                    var ids = [];
                    selectedData.forEach(function (data) {
                        ids.push(data.id);
                    });
                    var id = ids.join(",");
                    return id;
                }

                //出库单"批量删除"操作
                $scope.batchDelete = function () {
                    var id = getCurrentIds();
                    if(!id){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    kendo.ui.ExtOkCancelDialog.show({
                        title: "确认",
                        message: "确定要批量删除吗?",
                        icon: 'k-ext-question' }).then(function (resp) {
                        if (resp.button === 'OK') {
                            $sync(shipmentHeaderUrl + "/batch/delete/" + id, "DELETE").then(function (xhr) {
                                $scope.shipmentHeaderGrid.dataSource.read();
                            }),(function (xhr) {
                                $scope.shipmentHeaderGrid.dataSource.read();
                            })
                        }
                    });
                };

                //出库单"提交"操作
                $scope.submit = function () {
                    var id = getCurrentIds();
                    if(!id){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    $sync(shipmentHeaderUrl+"/submit/"+id,"PUT").then(function (xhr) {
                        $scope.shipmentHeaderGrid.dataSource.read();
                    }),(function (xhr){
                        $scope.shipmentHeaderGrid.dataSource.read();
                    })
                };


                //出库单"撤销"操作
                $scope.repealed = function () {
                    var id = getCurrentIds();
                    var idArr = id.split(",");
                    if(!id || idArr.length>1){
                        kendo.ui.ExtAlertDialog.showError("请选择一条数据.");
                        return;
                    }
                    kendo.ui.ExtOkCancelDialog.show({
                        title: "确认",
                        message: "出库单已分配确定要撤销吗?",
                        icon: 'k-ext-question' }).then(function (resp) {
                        if (resp.button === 'OK') {
                            $sync(shipmentHeaderUrl + "/repealed/" + id, "PUT").then(function (xhr) {
                                $scope.shipmentHeaderGrid.dataSource.read();
                            }),(function (xhr) {
                                $scope.shipmentHeaderGrid.dataSource.read();
                            })
                         }
                     });
                };

                //出库单"打印"操作
                $scope.printShipment = function () {
                    var ids = getCurrentIds();
                    if(!ids){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    var printLog = wmsReportPrint.printShipment(ids,1);
                };
                //拣货单"打印"操作
                $scope.printPicking = function () {
                    var ids = getCurrentIds();
                    if(!ids){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    var printLog = wmsReportPrint.printPicking(ids,1);
                };

                //更新库房Popup
                $scope.updateStorageRoom = function (data) {
                    $scope.storageRoomPopup.refresh().open().center();
                    $scope.storageRoomModel = {};
                    $scope.storageRoomModel.id = data.dataItem.id;
                    $scope.storageRoomModel.skuName = data.dataItem.skuName;
                    $scope.storageRoomModel.sku = data.dataItem.sku;
                    $scope.storageRoomModel.skuBarcode = data.dataItem.skuBarcode;
                    $scope.storageRoomModel.shipmentId = data.dataItem.shipmentId;
                    $scope.storageRoomModel.storageRoomIds = data.dataItem.storageRoomIds;
                }

                //关闭更新库房Popup
                $scope.updateStorageRoomClose = function () {
                    $scope.storageRoomModel = {};
                    $scope.storageRoomPopup.close();
                }

                //更新库房确认
                $scope.updateStorageRoomConfirm = function (e) {
                    var formValidator = $(e.target).parents(".k-edit-form-container").kendoValidator().data("kendoValidator");
                    if (!formValidator.validate()) {
                        return;
                    }
                    var storageRoomIds = $scope.storageRoomModel.storageRoomIds;
                    var storageRoomId = null;
                    for(var i = 0 ; i < storageRoomIds.length ; i ++){
                        storageRoomId = storageRoomId + storageRoomIds[i] + ",";
                    }
                    var params = {
                        id: $scope.storageRoomModel.id,
                        storageRoomId:storageRoomId,
                        shipmentId: $scope.storageRoomModel.shipmentId
                    };
                    //更新库房信息
                    $sync(shipmentHeaderUrl+"/storageRoom","PUT",{data: params})
                        .then(function (xhr) {
                            $scope.storageRoomModel = {};
                            $scope.storageRoomPopup.close();
                            $scope.shipmentHeaderGrid.dataSource.read();
                        }, function (xhr) {
                            $scope.shipmentHeaderGrid.dataSource.read();
                        });
                }
            });
        }]);
})