define(['scripts/controller/controller', '../../model/inwh/adjustmentModel'], function (controller, adjustment) {
    "use strict";
    controller.controller('adjustmentController',['$scope', '$rootScope', 'sync', 'wmsDataSource', 'wmsLog', '$filter', 'url',
        function ($scope, $rootScope, $sync, wmsDataSource, wmsLog, $filter, $url) {

           var adjustmentUrl = $url.inwhAdjustmentHeaderUrl,

                adjustmentHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    {title: ' 调整单号', field: 'id', align: 'left', width: "100px"},
                    { title: ' 货主', field: 'cargoOwnerId', align: 'left', width: "100px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: ' 调整类型', field: 'resonCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('resonCode','AdjustReason')},
                    { title: ' 单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: ' 备注', field: 'description', align: 'left', width: "100px"},
                ],
                adjustmentDetailColumns=[
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: ' 明细单号', field: 'id', align: 'left', width: "100px"},
                    { title: ' 参考单号', field: 'referDetailNo', align: 'left', width: "100px"},
                    { title: ' 指定库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                    { title: ' 商品编码', field: 'sku', align: 'left', width: "140px"},
                    { title: ' 商品名称', field: 'skuName', align: 'left', width: "140px"},
                    { title: ' 商品条码', field: 'barcode', align: 'left', width: "140px"},
                    { title: ' 调整前数量', field: 'adjustedBeforeQty', align: 'left', width: "100px"},
                    { title: ' 调整后数量', field: 'adjustedAfterQty', align: 'left', width: "100px"},
                    { title: ' 调整数量（正负）', field: 'adjustedQty', align: 'left', width: "140px"},
                    { title: ' 说明', field: 'description', align: 'left', width: "100px"},
                    { title: ' 单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                ],
                adjustmentDataSource = wmsDataSource({
                    url:adjustmentUrl,
                    schema:{
                        model:adjustment.adjustmentHeader
                    }
                });
            adjustmentHeaderColumns = adjustmentHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            adjustmentHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

            adjustmentDetailColumns = adjustmentDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            adjustmentDetailColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);


            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
               dataSource:adjustmentDataSource,
                toolbar:[{name: "create",text:"新增",className:'btn-auth-add'},
                    { name: "submit",  template: '<a class="k-button k-button-submit" ng-click="submit(dataItem)">提交</a>', className:'btn-auth-submit'},
                    {name:"reviewed", template: '<a class="k-button k-button-reviewed" ng-click="reviewed(dataItem)">审核</a>',className:'btn-auth-reviewed'}
                ],
                columns:adjustmentHeaderColumns,
                editable:{
                    mode:"popup",
                    window:{width:"700"},
                    template:kendo.template($("#adjustmentHeader-kendo-template").html())
                },
                widgetId:"header",
                dataBound:function (e) {
                    var grid = this,
                        trs = grid.tbody.find(">tr");
                    _.each(trs,function (tr,i){
                        var record = grid.dataItem(tr);
                        if(record.statusCode !== 'Initial' || record.resonCode === 'Move' || record.resonCode === 'Stocktaking'){
                            $(tr).find(".k-button").remove();
                        }
                    });
                },
                customChange:function (grid) {
                    $(".k-button-reviewed").hide();
                   $(".k-button-submit").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.adjustmentHeaderGrid);
                    if (selected.length>1){
                        kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据");
                        $scope.adjustmentHeaderGrid.dataSource.read();
                        return;
                    }
                    if (selected.length === 1){
                        var data = selected[0];
                        if(data.statusCode === 'Initial'){
                                $(".k-button-submit").show();
                        }
                        if(data.statusCode === 'Submitted'){
                            $(".k-button-reviewed").show();
                        }
                    }else {
                        $(".k-button-submit").show();
                        $(".k-button-reviewed").show();
                    }
                }
            }, $scope);


            $scope.adjustmentDetailOptions=function (dataItem) {
                var defaultOptions = {
                    dataSource:wmsDataSource({
                       url:adjustmentUrl + "/" + dataItem.id + "/detail",
                       schema:{
                           model:adjustment.adjustmentDetail
                       }
                    }),
                    columns:adjustmentDetailColumns,
                    widgetId:"detail",
                    editable:{
                        mode:"popup",
                        window:{
                            width:"600px"
                        },
                        template:kendo.template($("#adjustmentDetail-kendo-template").html())
                    },
                    toolbar:[
                        {name:"add",template:'<a class="k-button k-grid-add" ng-click="adjustDetailAdd(dataItem)">新增</a>',className:'btn-auth-add'},
                    ],
                    dataBound:function (e) {
                        var grid = this,
                            trs = grid.tbody.find(">tr");
                        if (dataItem.statusCode !== "Initial") {
                            grid.element.find(".k-grid-add").remove(); //明细toolbar按钮
                            _.each(trs, function (tr, i) {
                                $(tr).find(".k-button").remove();//列表按钮
                            });
                        }
                        if(dataItem.resonCode == "Move"){
                            grid.element.find(".k-grid-add").remove();
                            _.each(trs, function (tr, i) {
                                $(tr).find(".k-button").remove();//列表按钮
                            });
                        }
                        if(dataItem.resonCode == "Stocktaking"){
                            grid.element.find(".k-grid-add").remove();
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


            $scope.$on("kendoWidgetCreated", function (event, widget) {
                if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                    $scope.adjustmentDetailGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                //商品信息和在库数量选择
                $scope.windowOpen = function (parentGrid) {
                    var storageRoomId = parentGrid.$parent.storageRoomId;
                    if(storageRoomId=== undefined || storageRoomId === ""){
                        kendo.ui.ExtAlertDialog.showError("请先选择库房！");
                        return;
                    }
                    var cargoOwnerId = parentGrid.$parent.$parent.dataItem.cargoOwnerId;
                    var params={
                        cargoOwnerId:cargoOwnerId,
                        storageRoomId:storageRoomId
                    };
                    $scope.skuInventoryPopup.initParam = function (subScope) {
                        subScope.param = params;
                    };
                    $scope.skuInventoryPopup.refresh().open().center();
                    $scope.skuInventoryPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        //赋值s
                        $scope.editModel.set("cargoOwnerId", resultData.cargoOwnerId);
                        $scope.editModel.set("storageRoomId", resultData.storageRoomId);
                        $scope.editModel.set("skuId", resultData.skuId);
                        $scope.editModel.set("skuName", resultData.itemName);
                        $scope.editModel.set("adjustedBeforeQty", resultData.onhandQty);
                        $scope.editModel.set("adjustedQty", 0 - resultData.onhandQty);
                    };
                }


                $scope.adjustedAfterQtyChange = function () {
                    var adjustBeforeQty=adjustedBeforeQty.value;
                    var adjustafterQty1 = adjustedAfterQty.value;
                    var adjustQty=adjustafterQty1-adjustBeforeQty;
                    $scope.editModel.set("adjustedQty",adjustQty);
                }




                //调整单"提交"操作
                $scope.submit= function () {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.adjustmentHeaderGrid);

                    if(selectedData.length  === 0){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        $scope.adjustmentHeaderGrid.dataSource.read();
                        return;
                    }
                    var id =selectedData[0].id;
                    $sync(adjustmentUrl+"/submit/"+id,"PUT").then(function (xhr) {
                        $scope.adjustmentHeaderGrid.dataSource.read();
                    },function (xhr){
                        $scope.adjustmentHeaderGrid.dataSource.read();
                    })
                }

                //调整单"审核"操作
                $scope.reviewed=function () {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.adjustmentHeaderGrid);
                    if(selectedData.length === 0){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                        $scope.adjustmentHeaderGrid.dataSource.read();
                        return;
                    }
                    var id =selectedData[0].id;
                    $sync(adjustmentUrl+"/reviewed/"+id,"PUT").then(function (xhr) {
                        $scope.adjustmentHeaderGrid.dataSource.read();
                    },function (xhr){
                        $scope.adjustmentHeaderGrid.dataSource.read();
                    })
                }
            });


        }]);
})