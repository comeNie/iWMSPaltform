define(['scripts/controller/controller','../../model/inwh/asnModel'], function (controller, asn) {
    "use strict";
    controller.controller('asnController',['$scope', '$rootScope', 'wmsLog','sync', 'url', 'wmsDataSource',
        function($scope, $rootScope,wmsLog, $sync, $url, wmsDataSource) {
            var asnUrl = $url.inwhAsnHeaderUrl,
                asnHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '通知单号', field: 'id', align: 'left', width: "120px"},
                    { title: '数据来源', field: 'datasourceCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('datasourceCode','DataSource')},
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '入库类型', field: 'receiptTypeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('receiptTypeCode','ReceiptType')},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '收货状态', field: 'qcStatusCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('qcStatusCode','TicketStatus')},
                    { title: '单据来源', field: 'fromTypeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('fromTypeCode','ReceiptFrom')},
                    { title: '预约单号', field: 'warehouseReferNo', align: 'left', width: "120px"},
                    { title: '预计到货日期', field: 'expectedDate', align: 'left', width: "160px",template:WMS.UTILS.timestampFormat("expectedDate",'yyyy/MM/dd HH:mm:ss')},
                    { title: '起运地址', field: 'fromAddress', align: 'left', width: "120px"},
                    { title: '供应商', field: 'supplierName', align: 'left', width: "120px"},
                    {title: '承运商', field: 'carrierName', align: 'left', width: "120px"},
                    {title: '运输方式', field: 'transportModeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('transportModeCode','TransportModeType')},
                    {title: '总数量', field: 'totalQty', align: 'left', width: "120px"},
                    {title: '总金额', field: 'totalAmount', align: 'left', width: "120px"},
                    {title: '总净重', field: 'totalNetWeight', align: 'left', width: "120px"},
                    {title: '总毛重', field: 'totalGrossWeight', align: 'left', width: "120px"},
                    {title: '总体积', field: 'totalCube', align: 'left', width: "120px"},
                    {title: '实收总数量', field: 'totalQtyReal', align: 'left', width: "120px"},
                    {title: '备注', field: 'description', align: 'left', width: "120px"},
                ],

                asnDetailColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '明细单号', field: 'id', align: 'left', width: "120px"},
                    { title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                    { title: '商品条码', field: 'sku', align: 'left', width: "120px"},
                    { title: '货主商品编码', field: 'skuBarcode', align: 'left', width: "120px"},
                    { title: '期望数量', field: 'expectedQty', align: 'left', width: "120px"},
                    { title: '已收数量', field: 'receivedQty', align: 'left', width: "120px"},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '单价', field: 'price', align: 'left', width: "120px"},
                    { title: '单位', field: 'unitType', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { title: '规格', field: 'spec', align: 'left', width: "120px"},
                    { title: '最后收货时间', field: 'lastReceiptTime', align: 'left', width: "160px",template:WMS.UTILS.timestampFormat("lastReceiptTime",'yyyy/MM/dd HH:mm:ss')},
                    { title: '净重', field: 'netWeight', align: 'left', width: "120px"},
                    { title: '毛重', field: 'grossWeight', align: 'left', width: "120px"},
                    { title: '体积', field: 'volume', align: 'left', width: "120px"},
                    { title: '描述', field: 'description', align: 'left', width: "120px"},
                ],

                asnHeaderDataSource = wmsDataSource({
                    url: asnUrl,
                    schema: {
                        model:asn.asnDetail
                    }
                });

            asnHeaderColumns = asnHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            asnDetailColumns = asnDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            asnHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: asnHeaderDataSource,
                toolbar: [
                    { name: "create", text: "新增", className:'btn-auth-add'},
                    {name:"submit",template: '<a class="k-button k-button-submit" ng-click="submit(dataItem)">提交</a>',className:'btn-auth-submit'},
                    {name:"repealed",template: '<a class="k-button k-button-repealed" ng-click="repealed(dataItem)">撤销</a>',className:'btn-auth-repealed'},
                    {name:"createQc",template: '<a class="k-button k-button-createQc" ng-click="createQc(dataItem)">生成收货单</a>',className:'btn-auth-createQc'}
                ],
                columns: asnHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{width:"600"},
                    template: kendo.template($("#asnHeader-kendo-template").html())
                },
                widgetId:"header",
                dataBound:function (e) {
                    var grid = this,
                        trs = grid.tbody.find(">tr");
                    _.each(trs,function (tr,i) {
                        var record = grid.dataItem(tr);
                        if (record.statusCode !== 'Initial'){
                            $(tr).find(".k-button").remove();
                        }

                    });
                },
                customChange:function (grid) {
                    $(".k-button-submit").hide();
                    $(".k-button-createQc").hide();
                    $(".k-button-repealed").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.asnHeaderGrid);
                    if(selected.length >1){
                        kendo.ui.ExtAlertDialog.showError(" 最多只能选择一条数据!");
                        $scope.asnHeaderGrid.dataSource.read();
                        $(".k-button-submit").show();
                        $(".k-button-createQc").show();
                        $(".k-button-repealed").show();
                        return;
                    }
                     if((selected.length=== 1)){
                        var select=selected[0];
                        if (select.statusCode === "Initial"){
                            $(".k-button-submit").show();
                        }else if(select.statusCode === "Submitted"){
                            $(".k-button-createQc").show();
                            $(".k-button-repealed").show();
                        }else if (select.statusCode === "Finished"){
                            $(".k-button-submit").hide();
                            $(".k-button-createQc").hide();
                            $(".k-button-repealed").hide();
                        } else{
                            $(".k-button-submit").hide();
                            $(".k-button-createQc").hide();
                            $(".k-button-repealed").hide();
                        }
                    }else{
                         $(".k-button-submit").show();
                         $(".k-button-createQc").show();
                         $(".k-button-repealed").show();
                     }
                }


            }, $scope);

            //操作日志
            $scope.logOptions = wmsLog.operationLog;


            $scope.DetailOptions = function (dataItem) {
                var defaultOptions = {
                    dataSource: wmsDataSource({
                        url: asnUrl + "/" + dataItem.id+ "/detail",
                        schema: {
                            model: asn.asnHeader
                        },
                        callback:{
                            update:function (e) {
                                $scope.asnHeaderGrid.dataSource.read();
                            },
                            create:function () {
                                $scope.asnHeaderGrid.dataSource.read();
                            }
                        }
                    }),

                    columns: asnDetailColumns,
                    widgetId:"detail",
                    editable: {
                        mode: "popup",
                        window: {
                            width: "600px"
                        },
                        template: kendo.template($("#asnDetail-kendo-template").html())
                    },
                    toolbar: [{ name: "create", text: "新增", className: "btn-auth-add"}],
                    dataBound:function (e) {
                        var grid = this,
                            trs = grid.tbody.find(">tr");
                        if (dataItem.statusCode !== "Initial") {
                            grid.element.find(".k-grid-add").remove(); //明细toolbar按钮
                            _.each(trs, function (tr, i) {
                              $(tr).find(".k-button").remove();//列表按钮
                            });
                        }
                    },

                };

                return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
            };



            $scope.$on("kendoWidgetCreated", function (event, widget) {

                if (widget.options !== undefined && widget.options.widgetId === 'header') {
                    $scope.asnHeaderGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                //供应商选择
                $scope.windowOpenSupplier = function (parentGrid) {
                    var typeCode = "Supplier";
                    $scope.organizationsPopup.initParam = function (subScope) {
                        subScope.param = typeCode;
                    };
                    $scope.organizationsPopup.refresh().open().center();
                    $scope.organizationsPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                            //赋值
                            $scope.editModel.set("supplierId", resultData.id);
                            $scope.editModel.set("supplierName",resultData.name);
                            $scope.editModel.set("supplierContact", resultData.contacts);
                            $scope.editModel.set("supplierTelephone", resultData.telephone);
                            $scope.editModel.set("supplierAddress", resultData.address);
                    }
                }

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
                        $scope.editModel.set("carrierId",resultData.id);
                        $scope.editModel.set("carrierName",resultData.name);
                        $scope.editModel.set("carrierContact",resultData.contacts);
                        $scope.editModel.set("carrierTelephone",resultData.telephone);
                        $scope.editModel.set("carrierAddress",resultData.address);
                    }
                }



                $scope.submit = function () {
                    executeOperationRequest('submit');
                }


                $scope.repealed = function () {
                    executeOperationRequest('repealed');
                }

                $scope.createQc = function () {
                    executeOperationRequest('createQc');
                }

                var executeOperationRequest = function (type) {
                    var selectData = WMS.GRIDUTILS.getCustomSelectedData($scope.asnHeaderGrid);
                    if(selectData.length === 0){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据!");
                        return;
                    }
                    var id = selectData[0].id;
                    var url,method;
                    if ("createQc" === type){
                        url = asnUrl +"/createQc/"+id;
                        method="PUT";
                    }
                    if("submit" == type){
                        url = asnUrl+"/submit/"+id;
                        method="PUT";
                    }
                    if("repealed" == type){
                        url = asnUrl+"/repealed/"+id;
                        method="PUT";
                    }
                    $sync(url,method).then(function (xhr) {
                        $scope.asnHeaderGrid.dataSource.read();
                    },function (xhr) {
                        $scope.asnHeaderGrid.dataSource.read();
                    })

                }

            })


            //明细表选择商品
            $scope.$on("kendoWidgetCreated",function (event,widget) {
                if(widget.options !==undefined && widget.options.widgetId === 'detail'){
                    $scope.headerDetailGrid = widget;
                    widget.bind("edit",function(e){
                        $scope.editModel=e.model;
                    });
                }

                //商品选择
                $scope.windowOpenSku = function (parentGrid) {
                    $scope.skuPopup.refresh().open().center();
                    $scope.skuPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        $scope.editModel.set("skuId", resultData.id);
                        $scope.editModel.set("sku", resultData.sku);
                        $scope.editModel.set("skuName", resultData.itemName);
                        $scope.editModel.set("skuBarcode", resultData.barcode);
                        //$scope.editModel.set("price", resultData.costPrice);
                        $scope.editModel.set("spec",resultData.spec);
                        $scope.editModel.set("unitType",resultData.unitType);
                    };
                }

            })

        }]);

    })