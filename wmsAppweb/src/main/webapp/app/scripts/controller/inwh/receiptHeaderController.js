define(['scripts/controller/controller','../../model/inwh/receiptHeaderModel'], function (controller, receiptHeader) {
    "use strict";
    controller.controller('receiptHeaderController',['$scope', '$rootScope','wmsLog', 'sync', 'url', 'wmsDataSource','wmsReportPrint',
        function($scope, $rootScope, wmsLog, $sync, $url, wmsDataSource,wmsReportPrint) {
            var receiptHeaderUrl = $url.inwhReceiptHeaderUrl,
                receiptHeaderColumns = [
                    { title: '入库单号', field: 'id', align: 'left', width: "120px"},
                    { title: '通知单号', field: 'asnId', align: 'left', width: "120px"},
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '单据来源', field: 'fromTypeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('fromTypeCode','ReceiptFrom')},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '收货状态', field: 'receiptStatusCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('receiptStatusCode','ReceiptStatus')},
                    { title: '入库类型', field: 'receiptTypeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('receiptTypeCode','ReceiptType')},
                    { title: '质检状态', field: 'qcStatusCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('qcStatusCode','QualityStatus')},
                    { title: '收货总数量', field: 'totalQty', align: 'left', width: "120px"},
                    { title: '总净重(kg)', field: 'totalNetWeight', align: 'left', width: "120px"},
                    { title: '总毛重(kg)', field: 'totalGrossWeight', align: 'left', width: "120px"},
                    { title: '总体积(cm³)', field: 'totalVolume', align: 'left', width: "120px"},
                    { title: '收货人', field: 'receiptUser', align: 'left', width: "120px"},
                    { title: '收货日期', field: 'receiptDate', align: 'left', width: "200px",template: WMS.UTILS.timestampFormat("receiptDate")},
                    { title: '质检人', field: 'qcInspector', align: 'left', width: "120px"},
                    { title: '备注', field:'description', align: 'left', width: "120px"},
                ],
                receiptDetailColumns = [
                    { title: '明细单号', field: 'id', align: 'left', width: "120px"},
                    { title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                    { title: '商品类别', field: 'categorysId', align: 'left', width: "120px",template:WMS.UTILS.skuCategorysFormat("categorysId")},
                    { title: '商品编码', field: 'sku', align: 'left', width: "140px"},
                    { title: '货主商品条码', field: 'skuBarcode', align: 'left', width: "140px"},
                    { title: '商品用途', field: 'property', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('property','SkuProperty')},
                    { title: '库房编号', field: 'storageRoomId', align: 'left', width: "120px",template:WMS.UTILS.storageRoomStringFormat("storageRoomId", "storageRoomSrc")},
                    { title: '收货数量', field: 'receivedQty', align: 'left', width: "120px"},
                    { title: '收货时间', field: 'receiptTime', align: 'left', width: "180px",template: WMS.UTILS.timestampFormat("receiptTime")},
                    { title: '单价', field: 'costPrice', align: 'left', width: "120px"},
                    { title: '净重', field: 'netWeight', align: 'left', width: "120px"},
                    { title: '毛重', field: 'grossWeight', align: 'left', width: "120px"},
                    { title: '体积', field: 'volume', align: 'left', width: "120px"},
                    { title: '库存状态', field:'inventoryStatusCode', align:'left',width:"120px",template:WMS.UTILS.codeFormat('inventoryStatusCode','InventoryStatus')},
                    { title: '仓储温度', field: 'warehouseTemp', align: 'left', width: "120px"},
                    { title: '规格', field: 'spec', align: 'left', width: "120px"},
                    { title: '单位', field: 'unitType', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { title: '备注', field: 'description', align: 'left', width: "120px"},

                ],

                receiptHeaderDataSource = wmsDataSource({
                    url: receiptHeaderUrl,
                    schema: {
                        model:receiptHeader.receiptHeader
                    }
                });

            receiptHeaderColumns = receiptHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            receiptDetailColumns=receiptDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

            receiptHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;
            $scope.selectAllRow = WMS.GRIDUTILS.selectAllRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: receiptHeaderDataSource,
                toolbar: [
                    {name:"confirm",template: '<a class="k-button k-button-confirm" ng-click="confirm(dataItem)">确认</a>',className:'btn-auth-confirm'},
                    {name:"trans",template: '<a class="k-button k-button-trans" ng-click="trans(dataItem)">生成质检单</a>',className:'btn-auth-trans'},
                    { name: "storeprint", template: '<a class="k-button k-button-storeprint" ng-click="storeprint(dataItem)">入库单打印</a>', className:'btn-auth-storeprint'}
                ],
                columns: receiptHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{width:"600"},
                    template: kendo.template($("#receiptHeader-kendo-template").html())
                },
                widgetId:"header",


                customChange:function (grid) {
                    $(".k-button-confirm").show();
                    var selectData = WMS.GRIDUTILS.getCustomSelectedData($scope.receiptHeaderGrid);
                    // if(selected.length>1){
                    //     kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据");
                    //     return;
                    // }
                    for(var i = 0 ; i < selectData.length ; i ++){
                        if (selectData[i].statusCode !== 'Finished'){
                            $(".k-button-confirm").hide();
                        }
                        if (selectData[i].qcStatusCode !== 'Initial'){
                            $(".k-button-trans").hide();
                        }
                    }
                }
            }, $scope);


            //操作日志
            $scope.logOptions = wmsLog.operationLog;

            $scope.headerDetailOptions = function (dataItem) {
                var editQty;
                var defaultOptions = {
                    dataSource: wmsDataSource({
                        url: receiptHeaderUrl + "/" + dataItem.id+ "/detail",
                        callback:{
                            update: function (response, editData) {
                                dataItem.set("totalQty", dataItem.get("totalQty") - editQty + editData.receivedQty);
                                $scope.receiptHeaderGrid.dataSource.read();
                            },
                            create: function (response, editData) {
                                dataItem.set("totalQty", dataItem.get("totalQty") + editData.receivedQty);
                                $scope.receiptHeaderGrid.dataSource.read();
                            },
                            destroy: function (response, editData) {
                                dataItem.set("totalQty", dataItem.get("totalQty") - editData.receivedQty);
                                $scope.receiptHeaderGrid.dataSource.read();
                            }

                        },
                        schema: {
                            model: receiptHeader.receiptHeader
                        },
                        otherData:{"receiptId" : dataItem.id }
                    }),
                    columns: receiptDetailColumns,
                    edit:function (e) {
                        //初始值
                        editQty = e.model.receivedQty;
                    },
                    widgetId:"detail",
                    editable: {
                        mode: "popup",
                        window: {
                            width: "600px"
                        },
                        template: kendo.template($("#receiptDetail-kendo-template").html())
                    },
                    toolbar: toolbar,

                };
                return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
            };


            $scope.$on("kendoWidgetCreated", function (event, widget) {
                if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                    $scope.receiptDetailGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ////入库单操作相关

                //代储入库单"打印"操作
               $scope.storeprint = function () {
                   $(".k-grid-storeprint").attr("disable",true);
                   $scope.receiptPrint("storeprint");
               }

                //入库确认操作
               $scope.confirm = function () {
                   //获取选中的行
                   var selectData = WMS.GRIDUTILS.getCustomSelectedData($scope.receiptHeaderGrid);
                   if(selectData === undefined || selectData.length === 0){
                       kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                       return;
                   }
                   var ids = [];
                   selectData.forEach(function (data) {
                       ids.push(data.id);
                   });
                   $sync(receiptHeaderUrl+"/confirm/"+ids.toString(),"GET").then(function (xhr) {
                       $scope.receiptHeaderGrid.dataSource.read();
                   },function (xhr) {
                       $scope.receiptHeaderGrid.dataSource.read();
                   })
               }

               //入库单生成质检单
               $scope.trans = function () {
                   //获取选中的行
                   var selectData = WMS.GRIDUTILS.getCustomSelectedData($scope.receiptHeaderGrid);
                   if(selectData === undefined || selectData.length === 0){
                       kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                       return;
                   }
                   var ids = [];
                   selectData.forEach(function (data) {
                       ids.push(data.id);
                   });
                   $sync(receiptHeaderUrl+"/trans/"+ids.toString(),"GET").then(function (xhr) {
                       $scope.receiptHeaderGrid.dataSource.read();
                   },function (xhr) {
                       $scope.receiptHeaderGrid.dataSource.read();
                   })
               }


            });


            //入库单打印
            $scope.receiptPrint= function (printType){
                var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.receiptHeaderGrid);
                var selectedDataArray = _.map(selectedData, function (view) {
                    return view.id;
                });
                var receiptIds = selectedDataArray.join(",");
                if (receiptIds === "") {
                    kendo.ui.ExtAlertDialog.showError("请选择要打印的数据.");
                    return;
                }
                wmsReportPrint.printReceipts(receiptIds,1,printType);
            }

        }]);
})