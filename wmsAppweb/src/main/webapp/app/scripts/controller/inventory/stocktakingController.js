define(['scripts/controller/controller', '../../model/inwh/stocktakingModel'], function (controller, stocktaking) {
    "use strict";
    controller.controller('stocktakingController',['$scope', '$rootScope', 'sync', 'wmsDataSource', 'wmsLog', '$filter', 'url',
        function ($scope, $rootScope, $sync, wmsDataSource, wmsLog, $filter, $url) {

           var stocktakingurl = $url.inwhStocktakingHeaderUrl,

               stocktakingHeaderColumns = [
                 WMS.GRIDUTILS.CommonOptionButton(),
                   {title: ' 盘点单号', field: 'id', align: 'left', width: "100px"},
                   { title: ' 货主', field: 'cargoOwnerId', align: 'left', width: "100px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                   { title: ' 库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                   { title: ' 盘点类型', field: 'typeCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('typeCode','CycleCountType')},
                   { title: ' 盘点时间', field: 'stocktakingTime', align: 'left', width: "140px",template: WMS.UTILS.timestampFormat("stocktakingTime")},
                   { title: ' 单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                   { title: ' 总品种数', field: 'totalCategoryQty', align: 'left', width: "100px"},
                   { title: ' 总货位数', field: 'totalLocationQty', align: 'left', width: "100px"},
                   { title: ' 总库房数', field: 'totalStorageRoomQty', align: 'left', width: "100px"},
                   { title: ' 备注', field: 'description', align: 'left', width: "100px"},
                   { title: ' 提交者', field: 'submitUser', align: 'left', width: "100px"},
                   { title: ' 提交时间', field: 'submitTime', align: 'left', width: "140px",template: WMS.UTILS.timestampFormat("submitTime")},
               ],
               stocktakingDetailColumns =[
                   { title: '盘点', command: [{ name: "stocktaking",
                       template: "<a class='k-button k-button-icontext k-grid-stocktaking' ng-click='stocktaking(dataItem)' href='\\#'><span class='k-icon k-stocktaking'></span>盘点</a>"}],
                       width: "60px"
                   },
                   { title: ' 明细单号', field: 'id', align: 'left', width: "100px"},
                   { title: ' 指定库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                   { title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                   { title: '商品条形码', field: 'skuBarcode', align: 'left', width: "120px"},
                   { title: '在库可用数量', field: 'systemQty', align: 'left', width: "120px"},
                   { title: ' 上次盘点数量', field: 'countQty', align: 'left', width: "120px"},
                   { title: ' 本次盘点数量', field: 'recountQty', align: 'left', width: "120px"},
                   { title: ' 盘点次数', field: 'counter', align: 'left', width: "100px"},
                   { title: ' 是否已盘点', field: 'isTacked', align: 'left', width: "100px",template:WMS.UTILS.checkboxDisabledTmp("isTacked")},
                   { title: ' 盘点人', field: 'operationName', align: 'left', width: "100px"},
                   { title: ' 说明', field: 'description', align: 'left', width: "100px"},
               ],
               stocktakingDataSource = wmsDataSource({
                  url:stocktakingurl,
                  schema:{
                      model:stocktaking.stocktakingHeader
                  }
               });

            stocktakingHeaderColumns = stocktakingHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            stocktakingHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

            stocktakingDetailColumns = stocktakingDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            stocktakingDetailColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
               dataSource:stocktakingDataSource,
                toolbar:[
                    {name: "create",text:"新增",className:'btn-auth-add'},
                    {name:"submit",template: '<a class="k-button k-button-submit" ng-click="submit(dataItem)">提交</a>',className:'btn-auth-submit'},
                    {name:"confirm",template: '<a class="k-button k-button-confirm" ng-click="confirm(dataItem)">确认</a>',className:'btn-auth-confirm'}
                ],
                columns:stocktakingHeaderColumns,
                editable:{
                    mode:"popup",
                    window:{width:"700"},
                    template:kendo.template($("#stocktakingHeader-kendo-template").html())
                },
                widgetId:"header",
                dataBound:function (e) {
                    var grid = this,
                        trs = grid.tbody.find(">tr");
                    _.each(trs,function (tr,i) {
                        var record = grid.dataItem(tr);
                        if(record.statusCode !== 'Initial'){
                            $(tr).find(".k-button").remove();
                        }
                    });
                },



                customChange:function (grid) {
                    $(".k-button-submit").hide();
                    $(".k-button-confirm").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.stocktakingHeaderGrid);
                    if (selected.length>1){
                        kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据");
                        return;
                    }
                    if (selected.length===1){
                        var data = selected[0];
                        if (selected[0].statusCode === 'Initial'){
                            $(".k-button-submit").show();
                        }
                        if (selected[0].statusCode === 'Submitted'){
                            $(".k-button-confirm").show();
                        }
                    }else {
                        $(".k-button-submit").show();
                        $(".k-button-confirm").show();
                    }
                }

            }, $scope);

            $scope.stocktakingDetailOptions = function (dataItem) {
                var defaultOptions = {
                    dataSource:wmsDataSource({
                        url: stocktakingurl  + "/" + dataItem.id+ "/detail",
                        schema:{
                            model:stocktaking.stoktakingDeatil
                        }
                    }),
                    columns:stocktakingDetailColumns,
                    widgetId:"detail",
                    editable:{
                        mode:"popup",
                        window:{
                            width:"600px"
                        },
                    },
                    dataBound:function (e) {
                        var grid = this,
                            trs = grid.tbody.find(">tr");
                        if (dataItem.statusCode !== "Submitted") {
                            _.each(trs, function (tr, i) {
                                $(tr).find(".k-button").remove();//列表按钮
                            });
                        }
                    },
                };
                return WMS.GRIDUTILS.getGridOptions(defaultOptions,$scope);
            };


            //操作日志
            $scope.logOptions = wmsLog.operationLog;


            //作用域
            $scope.$on("kendoWidgetCreated", function (event, widget) {
                if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                    $scope.stocktakingDetailGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                //盘点单"提交"操作
                $scope.submit =function () {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.stocktakingHeaderGrid);
                    if(selectedData.length ===  0){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    var id =selectedData[0].id;
                    $sync(stocktakingurl+"/submit/"+id,"PUT").then(function (xhr) {
                        $scope.stocktakingHeaderGrid.dataSource.read();
                    },function (xhr){
                        $scope.stocktakingHeaderGrid.dataSource.read();
                    })
                    var printLog = wmsReportPrint.printShipment(ids,5);
                    if(printLog){
                        $(".k-grid-print").attr("disabled", "false");
                     }
                }

                //盘点单"确认"操作
                $scope.confirm =function () {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.stocktakingHeaderGrid);
                    if(selectedData.length === 0){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        return;
                    }
                    var id = selectedData[0].id;
                    $sync(stocktakingurl+"/confirm/"+id,"PUT").then(function (xhr) {
                        $scope.stocktakingHeaderGrid.dataSource.read();
                    },function (xhr){
                        $scope.stocktakingHeaderGrid.dataSource.read();
                    })
                }


                //盘点弹窗
                $scope.stocktaking = function (dataItem) {
                    $scope.stocktakingPopup.refresh().open().center();
                    $scope.stocktakingDetailModel = {};
                    $scope.stocktakingDetailModel.id = dataItem.id;
                    $scope.stocktakingDetailModel.skuName = dataItem.skuName;
                    $scope.stocktakingDetailModel.systemQty = dataItem.systemQty;
                }
                //盘点确定按钮
                $scope.stocktakingConfirm = function (e) {

                    var stocktakingDetailId = $scope.stocktakingDetailModel.id;
                    var countQty = $scope.stocktakingDetailModel.countQty;
                    var url = stocktakingurl+"/detail/submit/"+stocktakingDetailId;
                    if(countQty < 0){
                        kendo.ui.ExtAlertDialog.showError("请输入正整数！");
                        return;
                    }
                    var params = {
                        stocktakingDetailId:stocktakingDetailId,
                        countQty:countQty
                    };
                    $sync(url,"PUT",{data:params})
                        .then(function (xhr) {
                            $scope.stocktakingPopup.close();
                            $scope.stocktakingHeaderGrid.dataSource.read();
                         },function (xhr) {
                    //     //$scope.stocktakingHeaderGrid.dataSource.read();
                    //     //$scope.stocktakingPopup.close();
                     })
                }
                
                //取消窗口关闭
                $scope.stocktakingClose = function () {
                    $scope.stocktakingPopup.close();
                }
            });

        }]);
})