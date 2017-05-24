define(['scripts/controller/controller', '../../model/outwh/frozenModel'],function (controller, frozenModel) {
    "use strict";
    controller.controller('frozenController',['$scope', '$rootScope', 'wmsLog','sync', 'url', 'wmsDataSource',
        function ($scope, $rootScope, wmsLog ,$sync, $url, wmsDataSource) {

            var frozenUrl = $url.outwhFrozenUrl,
                frozenHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '质押单号', field: 'id', align: 'left', width: "100px"},
                    { title: '货主', field: 'cargoOwnerId', align: 'left', width: "100px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { title: '质押类型', field: 'factoringType', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('factoringType','FactoringType')},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '提交机构', field: 'factoringOrganizeId', align: 'left', width: "100px",template:WMS.UTILS.organizationsFormat("factoringOrganizeId")},
                    { title: '质押开始时间', field: 'factoringStartTime', align: 'left', width: "140px",template: WMS.UTILS.timestampFormat("factoringStartTime")},
                    { title: '质押结束时间', field: 'factoringEndTime', align: 'left', width: "140px",template: WMS.UTILS.timestampFormat("factoringEndTime")},
                    { title: '总数量', field: 'totalQty', align: 'left', width: "100px"},
                    { title: '总金额', field: 'totalAmount', align: 'left', width: "100px"},
                    { title: '审核人', field: 'auditedUser', align: 'left', width: "100px"},
                    { title: '审核机构', field: 'auditedOrganizeId', align: 'left', width: "100px",template:WMS.UTILS.organizationsFormat("auditedOrganizeId")},
                    { title: '审核时间', field: 'auditedTime', align: 'left', width: "160px",template: WMS.UTILS.timestampFormat("auditedTime")},
                    { title: '总净重', field: 'totalNetWeight', align: 'left', width: "100px"},
                    { title: '总毛重', field: 'totalGrossWeight', align: 'left', width: "100px"},
                    { title: '总体积', field: 'totalVolume', align: 'left', width: "100px"},
                    { title: '备注', field: 'description', align: 'left', width: "100px"},
                    { title: '是否已审核', field: 'isAudited', align: 'left', width: "100px",template:WMS.UTILS.checkboxDisabledTmp("isAudited")},
                ],
                frozenDetailColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '明细单号', field: 'id', align: 'left', width: "100px"},
                    { title: '单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                    { title: '商品条码', field: 'sku', align: 'left', width: "120px"},
                    { title: '批次号', field: 'workGroupNo', align: 'left', width: "100px"},
                    { title: '库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                    { title: '质押数量', field: 'factoringQty', align: 'left', width: "100px"},
                    { title: '总价', field: 'totalPrice', align: 'left', width: "100px"},
                    { title: '参考单价', field: 'factoringPrice', align: 'left', width: "100px"},
                    { title: '净重', field: 'netWeight', align: 'left', width: "100px"},
                    { title: '毛重', field: 'grossWeight', align: 'left', width: "100px"},
                    { title: '体积', field: 'volume', align: 'left', width: "100px"},
                    { title: '备注', field: 'description', align: 'left', width: "100px"},
                ],
                frozenHeaderDataSource = wmsDataSource({
                    url:frozenUrl,
                    schema:{
                        model:frozenModel.frozenHeader
                    }
                });
            frozenHeaderColumns = frozenHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            frozenHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

            frozenDetailColumns = frozenDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: frozenHeaderDataSource,
                toolbar:[
                    {name:"add",text:"新增",className:'btn-auth-add'},
                    {name:"submit", template:'<a class="k-button k-button-submit" ng-click="submit()">提交</a>', className:'btn-auth-submit'},
                    {name:"repealed",template:'<a class="k-button k-button-repealed" ng-click="repealed()">撤销</a>',className:'btn-auth-repealed'},
                    {name:"audit",template:'<a class="k-button k-button-audit" ng-click="audit()">审核</a>' ,className:'btn-auth-audit'},
                    {name:"reject",template:'<a class="k-button k-button-reject" ng-click="frozenReject()">拒绝</a>',className:'btn-auth-reject'},
                ],
                columns:frozenHeaderColumns,
                editable:{
                    mode:"popup",
                    window:{width:"700"},
                    template:kendo.template($("#frozenHeader-kendo-template").html())
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
                    $(".k-button-repealed").hide();
                    $(".k-button-reject").hide();
                    $(".k-button-audit").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.frozenHeaderGrid);
                    if (selected.length >1){
                        kendo.ui.ExtAlertDialog.showError(" 最多只能选择一条数据!");
                        $scope.frozenHeaderGrid.dataSource.read();
                        return;
                    }
                    if(selected.length === 1){
                        var data = selected[0];
                        if(data.statusCode === 'Initial'){
                            $(".k-button-submit").show();
                        }else if(data.statusCode === 'Submitted'){
                            $(".k-button-repealed").show();
                            $(".k-button-reject").show();
                            $(".k-button-audit").show();
                        }
                    } else {
                        $(".k-button-submit").show();
                        $(".k-button-repealed").show();
                        $(".k-button-reject").show();
                        $(".k-button-audit").show();
                    }
                }
            }, $scope);

            $scope.headerDetailOptions = function (dataItem) {
                var defaultOptions = {
                    dataSource:wmsDataSource({
                        url: frozenUrl + "/" + dataItem.id+ "/detail",
                        schema: {
                            model: frozenModel.frozenHeader
                        },
                        callback:{
                            update:function (e) {
                                $scope.frozenHeaderGrid.dataSource.read();
                            },
                            create:function (e) {
                                $scope.frozenHeaderGrid.dataSource.read();
                            },
                            save:function (e) {
                                $scope.frozenHeaderGrid.dataSource.read();
                            },
                            destroy:function (e) {
                                $scope.frozenHeaderGrid.dataSource.read();
                            }
                        }
                    }),
                    columns:frozenDetailColumns,
                    widgetId:"detail",
                    editable:{
                        mode:"popup",
                        window:{width:"600px"},
                        template:kendo.template($("#frozenDetail-kendo-template").html())
                    },
                    toolbar:[{name:"create",text:"新增",className:'btn-auth-add'}],
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
                return WMS.GRIDUTILS.getGridOptions(defaultOptions,$scope);
            };



            //操作日志
            $scope.logOptions = wmsLog.operationLog;




            $scope.$on("kendoWidgetCreated",function (event,widget) {
                if (widget.options !== undefined && widget.options.widgetId === 'header') {
                    $scope.frozenHeaderGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                $scope.frozenReject = function () {
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.frozenHeaderGrid);
                    if(selected.length === 0){
                        kendo.ui.ExtAlertDialog.showError("请先选择单据!");
                        $scope.frozenHeaderGrid.dataSource.read();
                        return;
                    }
                    $scope.frozenRejectPopup.refresh().open().center();
                    $scope.frozenRejectModel = {};
                    $scope.frozenRejectModel.frozenId= selected[0].id;
                }


                //审核拒绝
                $scope.frozenRejectConfirm = function (e) {
                    var formValidator = $(e.target).parents(".k-edit-form-container").kendoValidator().data("kendoValidator");
                    if (!formValidator.validate()) {
                        return;
                    }
                    var frozenId = $scope.frozenRejectModel.frozenId;
                    var rejectReason = $scope.frozenRejectModel.rejectReason;
                    if(rejectReason === undefined || rejectReason ===""){
                        kendo.ui.ExtAlertDialog.showError("拒绝原因必须填写！")
                        return ;
                    }
                    var url = frozenUrl+"/reject/"+frozenId;
                    var params = {
                        frozenId:frozenId,
                        rejectReason:rejectReason
                    };
                    $sync(url,"PUT",{data:params})
                        .then(function (xhr) {
                            $scope.frozenRejectPopup.close();
                            $scope.frozenHeaderGrid.dataSource.read();
                        },function (xhr) {
                        $scope.frozenHeaderGrid.dataSource.read();
                        $scope.frozenRejectPopup.close();
                    })

                }

                //弹窗关闭
                $scope.frozenRejectClose = function () {
                    $scope.frozenRejectPopup.close();
                }

            });



            $scope.$on("kendoWidgetCreated", function (event, widget) {
                if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                    $scope.frozenDetailGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                //库存选择
                $scope.windowOpen = function (parentGrid) {
                    var cargoOwnerId = parentGrid.$parent.$parent.dataItem.cargoOwnerId;
                    $scope.frozenInventoryPopup.initParam = function (subScope) {
                        subScope.param = cargoOwnerId;
                    };
                    $scope.frozenInventoryPopup.refresh().open().center();
                    $scope.frozenInventoryPopup.setReturnData = function (resultData) {

                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        //赋值
                        $scope.editModel.set("skuId", resultData.skuId);
                        $scope.editModel.set("sku",resultData.sku);
                        $scope.editModel.set("skuName", resultData.itemName);
                        $scope.editModel.set("skuBarcode", resultData.barcode);
                        $scope.editModel.set("spec",resultData.spec);
                        $scope.editModel.set("unitType",resultData.unitType);
                        $scope.editModel.set("costPrice",resultData.costPrice);
                        $scope.editModel.set("storageRoomId",resultData.storageRoomId);
                        $scope.editModel.set("maxFrozenQty",resultData.mortgagedAbleQty);
                    };
                }

                $scope.alertChoseSku = function (parentGrid) {
                    var sku = $('#skuName').val();
                    if(sku === undefined || sku === ""){
                        kendo.ui.ExtAlertDialog.showError("请先选择商品！");
                        return;
                    }
                    var factoringQty = $('#factoringQty').val();
                    var maxFrozenQty = $scope.editModel.maxFrozenQty;
                    var factoringPrice = $('#factoringPrice').val();
                    if(factoringQty>maxFrozenQty){
                        kendo.ui.ExtAlertDialog.showError(sku+"最多只能质押"+maxFrozenQty+"!");
                        return;
                    }else{
                        $scope.editModel.set("totalPrice",factoringPrice*factoringQty);
                    }
                }


                //根据输入数量和质押参考单价计算质押总金额
                $scope.computeTotalAmount = function () {
                    var factoringQty = $('#factoringQty').val();
                    if(factoringQty === undefined || factoringQty=== ""){
                        kendo.ui.ExtAlertDialog.showError("请先输入质押数量！");
                        return;
                    }
                    var factoringPrice = $('#factoringPrice').val();
                    $scope.editModel.set("totalPrice",factoringQty*factoringPrice);
                }


                $scope.submit = function () {
                    executeOperationRequest('submit');
                }

                $scope.repealed = function () {
                    executeOperationRequest('repealed');
                }

                $scope.audit = function () {
                    executeOperationRequest('audit');
                }

                //执行请求
                var executeOperationRequest = function (type) {
                    var selectData = WMS.GRIDUTILS.getCustomSelectedData($scope.frozenHeaderGrid);
                    if(selectData.length === 0){
                        kendo.ui.ExtAlertDialog.showError("请至少选择一条数据!");
                        $scope.frozenHeaderGrid.dataSource.read();
                        return;
                    };
                    var id = selectData[0].id;
                    var url,method;
                    if ("submit" === type){
                        url = frozenUrl +"/submit/"+id;
                        method="PUT";
                    }
                    if("repealed" == type){
                        url = frozenUrl+"/repealed/"+id;
                        method="PUT";
                    }
                    if("audit" === type){
                        url = frozenUrl+"/audit/"+id;
                        method = "PUT";
                    }
                    $sync(url,method).then(function (xhr) {
                        $scope.frozenHeaderGrid.dataSource.read();
                    },function (xhr) {
                        $scope.frozenHeaderGrid.dataSource.read();
                    })
                }

            });

        }]);
})