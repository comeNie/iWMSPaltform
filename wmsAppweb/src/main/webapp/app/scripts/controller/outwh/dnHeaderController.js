define(['scripts/controller/controller', '../../model/outwh/dnHeaderModel'], function (controller, dnHeader) {
    "use strict";
    controller.controller('dnHeaderController', ['$scope', '$rootScope', 'wmsLog', 'allocatedLog', 'sync', 'url', 'wmsDataSource',
        function ($scope, $rootScope, wmsLog, allocatedLog, $sync, $url, wmsDataSource) {

            $scope.order = {};
            $scope.invoice = {};

            var dnHeaderUrl = $url.outwhDnHeaderUrl,
                dnHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    {title: '发货通知单号', field: 'id', align: 'left', width: "120px"},
                    {title: ' 数据来源', field: 'datasourceCode', align: 'left', width: "100px", template: WMS.UTILS.codeFormat('datasourceCode', 'DataSource')},
                    {title: ' 货主', field: 'cargoOwnerId', align: 'left', width: "200px", template: WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    {title: ' 平台订单号', field: 'orderId', align: 'left', width: "150px"},
                    {title: ' 订单来源', field: 'fromtypeCode', align: 'left', width: "100px", template: WMS.UTILS.codeFormat('fromtypeCode', 'ShipmentFrom')},
                    {title: ' 订单类型', field: 'typeCode', align: 'left', width: "100px", template: WMS.UTILS.codeFormat('typeCode', 'OrderTypeCode')},
                    {title: ' 单据状态', field: 'statusCode', align: 'left', width: "100px", template: WMS.UTILS.codeFormat('statusCode', 'TicketStatus')},
                    {title: ' 承运商名称', field: 'carrierName', align: 'left', width: "100px"},
                    //{ title: ' 订单审核时间', field: 'auditedTime', align: 'left', width: "120px"},
                    //{ title: ' 订单审核人', field: 'auditedUser', align: 'left', width: "100px"},
                ],

                dnDetailColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    {title: '明细单号', field: 'id', align: 'left', width: "120px"},
                    {title: '商品编码', field: 'sku', align: 'left', width: "100px"},
                    {title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                    {title: '货主商品条码', field: 'skuBarcode', align: 'left', width: "120px"},
                    {title: '数量', field: 'qty', align: 'left', width: "120px"},
                    {title: '期望库房', field: 'storageRoomId', align: 'left', width: "120px", template: WMS.UTILS.storageRoomStringFormat("storageRoomId", "storageRoomSrc")},
                    {title: '原价', field: 'price', align: 'left', width: "120px"},
                    {title: '原金额', field: 'amount', align: 'left', width: "120px"},
                    {title: '实付金额', field: 'payment', align: 'left', width: "120px"},
                    {title: '折扣金额', field: 'discountFee', align: 'left', width: "120px"},
                    //{ title: '手工调整金额', field: 'adjustFee', align: 'left', width: "120px"},
                    //{ title: '是否赠品', field: 'isGift', align: 'left', width: "100px",template:WMS.UTILS.checkboxDisabledTmp("isGift")}
                ],

                dnHeaderDataSource = wmsDataSource({
                    url: dnHeaderUrl,
                    schema: {
                        model: dnHeader.dnHeader
                    },
                    otherData: {"order": $scope.order, "invoice": $scope.invoice, "invoice.invoiceTypeCode": ""}
                });
            dnHeaderColumns = dnHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            dnHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            dnDetailColumns = dnDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;
            //$scope.selectAllRow = WMS.GRIDUTILS.selectAllRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: dnHeaderDataSource,
                toolbar: [{name: "create", text: "新增", className: 'btn-auth-add'},
                    {
                        name: "submit",
                        template: '<a class="k-button k-button-submit " ng-click="submit()">提交</a>',
                        className: 'btn-auth-submit'
                    }],
                columns: dnHeaderColumns,
                widgetId: "header",

                editable: {
                    mode: "popup",
                    window: {
                        width: "600"
                    },
                    template: kendo.template($("#dnHeader-kendo-template").html())
                },
                dataBound: function (e) {
                    var grid = this,
                        trs = grid.tbody.find(">tr");
                    _.each(trs, function (tr, i) {
                        var record = grid.dataItem(tr);
                        if (record.statusCode !== 'Initial') {
                            $(tr).find(".k-button").remove();
                        }

                    });
                },
                customChange: function (grid) {
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.dnHeaderGrid);
                    if (selected.length > 1) {
                        kendo.ui.ExtAlertDialog.showError(" 最多只能选择一条数据!");
                        return;
                    }
                    if (selected.length === 1) {
                        var data = selected[0];
                        if (data.statusCode === 'Submitted') {
                            $(".k-button-submit").hide();
                        }
                        if (data.statusCode === 'Finished') {
                            $(".k-button-submit").hide();
                        }
                    } else {
                        $(".k-button-submit").show();
                    }
                }
            }, $scope);


            $scope.headerDetailOptions = function (dataItem) {

                var defaultOptions = {
                    dataSource: wmsDataSource({
                        url: dnHeaderUrl + "/" + dataItem.id + "/detail",
                        schema: {
                            model: dnHeader.dnDetail
                        }
                    }),
                    columns: dnDetailColumns,
                    widgetId: "detail",

                    editable: {
                        mode: "popup",
                        window: {
                            width: "600px"
                        },
                        template: kendo.template($("#dnDetail-kendo-template").html())
                    },
                    toolbar: [{name: "create", text: "新增", className: 'btn-auth-add'}],
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
                };

                return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
            };

            //操作日志
            $scope.logOptions = wmsLog.operationLog;

            //库存分配记录
            $scope.allocateOptios = allocatedLog.allocateLog;


            //基本信息
            $scope.dnBaseOptions = function (dataItem) {
                $sync(dnHeaderUrl + "/" + dataItem.id + "/detail/basic", "GET")
                    .then(function (xhr) {
                        dataItem.order = xhr.result.order;
                        dataItem.invoice = xhr.result.invoice;
                    });
            };

            //MultiSelect
            var dataSource;
            $sync(window.BASEPATH + "/index/storageRoom/warehouseId",'GET').then(function (data) {
                dataSource = data.result;
            })

            $scope.$on("kendoWidgetCreated", function (event, widget) {

                //MultiSelect
                $scope.selectOptions = {
                    placeholder: "请选择库房",
                    dataTextField: "key",
                    dataValueField: "value",
                    valuePrimitive: true,
                    autoBind: false,
                    dataSource:dataSource
                };

                if (widget.options !== undefined && widget.options.widgetId === 'header') {
                    widget.bind("edit", function (e) {
                        if (!e.model.order) {
                            e.model.order = {isCod: "", receiverName: "", address: "", mobile: ""};
                        }
                        if (!e.model.invoice) {
                            e.model.invoice = {invoiceTypeCode: ""};
                        }
                    });
                }


                if (widget.options !== undefined && widget.options.widgetId === 'header') {
                    $scope.dnHeaderGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.dnHeader = e.model;
                    });


                    var getCurrentIds = function () {
                        //获取选中的行
                        var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.dnHeaderGrid);
                        var ids = [];
                        selectedData.forEach(function (data) {
                            ids.push(data.id);
                        });
                        var id = ids.join(",");
                        return id;
                    }


                    //通知单提交后台生成出库单
                    $scope.submit = function () {
                        var id = getCurrentIds();
                        var arr = id.split(",");
                        if (arr.length > 1 || !id) {
                            kendo.ui.ExtAlertDialog.showError("请选择一条要提交的数据.");
                            return;
                        }
                        $sync(dnHeaderUrl + "/submit/" + id, "PUT").then(function (xhr) {
                            $scope.dnHeaderGrid.dataSource.read();
                        }), (function (xhr) {
                            $scope.dnHeaderGrid.dataSource.read();
                        })
                    };


                }

                if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                    $scope.dnDetailGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }
                

                //货主库存商品选择
                $scope.windowOpen = function (parentGrid) {
                    var cargoOwnerId = parentGrid.$parent.dataItem.cargoOwnerId;
                    $scope.dnSkuInventoryPopup.initParam = function (subScope) {
                        subScope.param = cargoOwnerId;
                    };
                    $scope.dnSkuInventoryPopup.refresh().open().center();
                    $scope.dnSkuInventoryPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        //赋值
                        $scope.editModel.set("skuId", resultData.skuId);
                        $scope.editModel.set("sku", resultData.sku);
                        $scope.editModel.set("skuName", resultData.skuName);
                        $scope.editModel.set("skuBarcode", resultData.skuBarcode);
                        $scope.editModel.set("spec", resultData.spec);
                    };
                };
                //货主商品选择
                // $scope.windowOpen = function (parentGrid) {
                //     var cargoOwnerId = parentGrid.$parent.dataItem.cargoOwnerId;
                //     $scope.outWhSkuPopup.initParam = function (subScope) {
                //         subScope.param = cargoOwnerId;
                //     };
                //     $scope.outWhSkuPopup.refresh().open().center();
                //     $scope.outWhSkuPopup.setReturnData = function (resultData) {
                //         if (_.isEmpty(resultData)) {
                //             return;
                //         }
                //         //赋值
                //         $scope.editModel.set("skuId", resultData.id);
                //         $scope.editModel.set("sku", resultData.sku);
                //         $scope.editModel.set("skuName", resultData.itemName);
                //         $scope.editModel.set("skuBarcode", resultData.cargoOwnerBarcode);
                //         $scope.editModel.set("spec", resultData.spec);
                //     };
                // }

                //承运商选择
                $scope.windowOpenCarrier = function (parentGrid) {
                    var typeCode = "Carrier";
                    $scope.organizationsPopup.initParam = function (subScope) {
                        subScope.param = typeCode;
                    };
                    $scope.organizationsPopup.refresh().open().center();
                    $scope.organizationsPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        //赋值
                        $scope.dnHeader.set("carrierId", resultData.id);
                        $scope.dnHeader.set("carrierName", resultData.name);
                    }
                }
                //客户选择
                $scope.windowOpenCustomer = function (parentGrid) {
                    $scope.customerPopup.refresh().open().center();
                    $scope.customerPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        //赋值
                        $scope.dnHeader.set("order", {receiverName:resultData.customerName,address:resultData.address,mobile:resultData.telephone});
                    }
                }


                //通知单checkbox 赋值
                $scope.isSelected = function (isUrgent) {
                    var value = isUrgent;
                    console.log(value);
                }
            })


        }]);
})