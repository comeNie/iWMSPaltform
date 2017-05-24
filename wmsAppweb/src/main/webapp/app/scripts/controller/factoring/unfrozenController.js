define(['scripts/controller/controller', '../../model/outwh/unfrozenModel'], function (controller, unfrozenModel) {
    "use strict";
    controller.controller('unfrozenController',['$scope', '$rootScope', 'sync', 'wmsDataSource', 'wmsLog', '$filter', 'url',
            function ($scope, $rootScope, $sync, wmsDataSource, wmsLog, $filter, $url) {

                var unfrozenUrl = $url.outwhUnfrozenUrl,
                    unfrozenHeaderColumns = [
                        { title: '解押单号', field: 'id', align: 'left', width: "100px"},
                        { title: '质押单号', field: 'frozenId', align: 'left', width: "100px"},
                        { title: '货主', field: 'cargoOwnerId', align: 'left', width: "100px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                        { title: '单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                        { title: '质押总数量', field: 'totalQty', align: 'left', width: "100px"},
                        { title: '质押总金额', field: 'totalAmount', align: 'left', width: "100px"},
                        { title: '已解押总数量', field: 'factoringTotalQty', align: 'left', width: "120px"},
                        { title: '预计解押时间', field: 'factoringTime', align: 'left', width: "140px",template: WMS.UTILS.timestampFormat("factoringTime")},
                        { title: '最近一次解押时间', field: 'factoringActualTime', align: 'left', width: "140px",template: WMS.UTILS.timestampFormat("factoringActualTime")},
                        { title: '解押类型', field: 'factoringType', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('factoringType','UnfrozenTypeCode')},
                        { title: '总净重', field: 'totalNetWeight', align: 'left', width: "100px"},
                        { title: '总毛重', field: 'totalGrossWeight', align: 'left', width: "100px"},
                        { title: '总体积', field: 'totalVolume', align: 'left', width: "100px"},
                        { title: '提交机构', field: 'factoringOrganizeId', align: 'left', width: "100px",template:WMS.UTILS.organizationsFormat("factoringOrganizeId")},
                        { title: '审核机构', field: 'auditedOrganizeId', align: 'left', width: "100px",template:WMS.UTILS.organizationsFormat("auditedOrganizeId")},
                        { title: '审核人', field: 'auditedUser', align: 'left', width: "100px"},
                        { title: '审核时间', field: 'auditedTime', align: 'left', width: "160px",template: WMS.UTILS.timestampFormat("auditedTime")},
                        { title: '备注', field: 'description', align: 'left', width: "100px"},
                        { title: '是否审核', field: 'isAudited', align: 'left', width: "100px",template:WMS.UTILS.checkboxDisabledTmp("isAudited")},
                        { title: '是否作废', field: 'isInvalided', align: 'left', width: "100px",template:WMS.UTILS.checkboxDisabledTmp("isInvalided")},
                    ],
                    unfrozenDetailColumns=[

                        { title: '解押', command: [{ name: "unfrozen",
                            template: "<a class='k-button k-button-icontext k-grid-unfrozen' ng-click='unfrozen(dataItem)' href='\\#'><span class='k-icon k-unfrozen'></span>解押</a>"}],
                            width: "60px"
                        },
                        { title: '明细单号', field: 'id', align: 'left', width: "100px"},
                        { title: '单据状态', field: 'statusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                        { title: '解押状态', field: 'factoringStatusCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('factoringStatusCode','FactoringStatus')},
                        { title: '商品条码', field: 'sku', align: 'left', width: "120px"},
                        { title: '商品名称', field: 'skuName', align: 'left', width: "120px"},
                        { title: '质押金额', field: 'totalPrice', align: 'left', width: "120px"},
                        { title: '原质押数量', field: 'frozenedQty', align: 'left', width: "120px"},
                        { title: '已解押数量', field: 'unfrozenedQty', align: 'left', width: "120px"},
                        { title: '本次解押数量', field: 'factoringQty', align: 'left', width: "160px"},
                        //{ title: '批次号', field: 'workGroupNo', align: 'left', width: "100px"},
                        { title: '所在库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                        { title: '净重', field: 'netWeight', align: 'left', width: "100px"},
                        { title: '毛重', field: 'grossWeight', align: 'left', width: "100px"},
                        { title: '体积', field: 'volume', align: 'left', width: "100px"},
                        { title: '备注', field: 'description', align: 'left', width: "100px"},
                    ],
                    unfrozenDataSource = wmsDataSource({
                        url:unfrozenUrl,
                        schema:{
                            model:unfrozenModel.unfrozenHeader
                        }
                    });
                unfrozenHeaderColumns = unfrozenHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
                unfrozenHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

                unfrozenDetailColumns = unfrozenDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

                $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

                $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                    dataSource: unfrozenDataSource,
                    toolbar:[
                        {name:"submit",template:'<a class="k-button k-button-submit" ng-click="UnfrozenSubmit()">提交</a>',className:'btn-auth-submit'},
                        {name:"repealed",template:'<a class="k-button k-button-repealed" ng-click="repealed()">撤销</a>',className:'btn-auth-repealed'},
                        {name:"reject",template:'<a class="k-button k-button-reject" ng-click="unfrozenReject()">拒绝</a>',className:'btn-auth-reject'},
                        {name:"audit",template:'<a class="k-button k-button-audit" ng-click="audit()">审核通过</a>',className:'btn-auth-audit'},
                        {name:"invalid",template:'<a class="k-button k-button-invalid" ng-click="unfrozenInvalid()">作废</a>',className:'btn-auth-invalid'},
                    ],
                    columns:unfrozenHeaderColumns,
                    editable:{
                        mode:"popup",
                        window:{width:"700"},
                    },
                    widgetId:"header",
                    customChange:function (grid) {
                        $(".k-button-submit").hide();
                        $(".k-button-repealed").hide();
                        $(".k-button-reject").hide();
                        $(".k-button-audit").hide();
                        $(".k-button-invalid").hide();
                        var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.unfrozenGrid);
                        if(selected.length>1){
                            kendo.ui.ExtAlertDialog.showError(" 最多只能选择一条数据!");
                            $scope.unfrozenGrid.dataSource.read();
                            return;
                        }
                        if(selected.length === 1){
                            var data=selected[0];
                            if(data.statusCode === "Initial"){
                                $(".k-button-submit").show();
                                $(".k-button-invalid").show();
                            }else if(data.statusCode === "Submitted"){
                                $(".k-button-repealed").show();
                                $(".k-button-audit").show();
                                $(".k-button-reject").show();
                            }
                        }else {
                            $(".k-button-submit").show();
                            $(".k-button-repealed").show();
                            $(".k-button-reject").show();
                            $(".k-button-audit").show();
                            $(".k-button-invalid").show();
                        }
                    }

                }, $scope);


                $scope.unfrozenDetailOptions = function (dataItem) {

                    var defaultOptions = {
                        dataSource:wmsDataSource({
                            url: unfrozenUrl + "/" + dataItem.id+ "/detail",
                            schema: {
                                model: unfrozenModel.unfrozenDetail
                            }
                        }),
                        columns:unfrozenDetailColumns,
                        widgetId:"detail",
                        editable:{
                            mode:"popup",
                            window:{width:"600px"},
                        },
                        dataBound:function (e) {
                            var grid = this,
                                trs = grid.tbody.find(">tr");
                            _.each(trs,function (tr,i) {
                                var record = grid.dataItem(tr);
                                if(record.statusCode !== 'Initial'){
                                    $(tr).find(".k-grid-unfrozen").remove();
                                }
                            });
                        },
                    };
                    return WMS.GRIDUTILS.getGridOptions(defaultOptions,$scope);
                };



                //操作日志
                $scope.logOptions = wmsLog.operationLog;


                $scope.$on("kendoWidgetCreated", function (event, widget) {
                    if (widget.options !== undefined && widget.options.widgetId === 'header') {
                        $scope.unfrozenGrid = widget;
                        widget.bind("edit", function (e) {
                            $scope.editModel = e.model;
                        });
                    }

                    //审核拒绝弹窗
                    $scope.unfrozenReject = function () {
                        var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.unfrozenGrid);
                        if(selected.length === 0){
                            kendo.ui.ExtAlertDialog.showError("请至少选择一条数据!");
                            $scope.unfrozenGrid.dataSource.read();
                            return;
                        }
                        $scope.unfrozenRejectPopup.refresh().open().center();
                        $scope.unfrozenRejectModel = {};
                        $scope.unfrozenRejectModel.unfrozenId= selected[0].id;
                    }


                    //审核拒绝
                    $scope.unfrozenRejectConfirm = function (e) {
                        var formValidator = $(e.target).parents(".k-edit-form-container").kendoValidator().data("kendoValidator");
                        if (!formValidator.validate()) {
                            return;
                        }
                        var unfrozenId = $scope.unfrozenRejectModel.unfrozenId;
                        var rejectReason = $scope.unfrozenRejectModel.rejectReason;
                        if(rejectReason === undefined || rejectReason === ""){
                            kendo.ui.ExtAlertDialog.showError("拒绝原因必须填写！")
                            return ;
                        }
                        var url = unfrozenUrl+"/reject/"+unfrozenId;
                        var params = {
                            unfrozenId:unfrozenId,
                            rejectReason:rejectReason
                        };
                        $sync(url,"PUT",{data:params})
                            .then(function (xhr) {
                                $scope.unfrozenRejectPopup.close();
                                $scope.unfrozenGrid.dataSource.read();
                            },function (xhr) {
                            $scope.unfrozenGrid.dataSource.read();
                            $scope.unfrozenRejectPopup.close();
                        })
                    }

                    //弹窗关闭
                    $scope.unfrozenRejectClose = function () {
                        $scope.unfrozenRejectPopup.close();
                    }


                    //作废解压单
                    $scope.unfrozenInvalid = function () {
                        var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.unfrozenGrid);
                        if(selected.length === 0){
                            kendo.ui.ExtAlertDialog.showError("请至少选择一条数据!");
                            $scope.unfrozenGrid.dataSource.read();
                            return;
                        }
                        $scope.unfrozenInvalidPopup.refresh().open().center();
                        $scope.unfrozenInvalidModel = {};
                        $scope.unfrozenInvalidModel.unfrozenId= selected[0].id;
                    }


                    //作废解押单确定
                    $scope.unfrozenInvalidConfirm = function (e) {
                        var formValidator = $(e.target).parents(".k-edit-form-container").kendoValidator().data("kendoValidator");
                        if (!formValidator.validate()) {
                            return;
                        }
                        var unfrozenId = $scope.unfrozenInvalidModel.unfrozenId;
                        var invalidReason = $scope.unfrozenInvalidModel.invalidReason;
                        var url = unfrozenUrl+"/invalid/"+unfrozenId;
                        var params = {
                            unfrozenId:unfrozenId,
                            invalidReason:invalidReason
                        };
                        $sync(url,"PUT",{data:params})
                            .then(function (xhr) {
                                $scope.unfrozenInvalidPopup.close();
                                $scope.unfrozenGrid.dataSource.read();
                            },function (xhr) {
                            $scope.unfrozenGrid.dataSource.read();
                            $scope.unfrozenInvalidPopup.close();
                        })
                    }

                    //取消关闭弹窗
                    $scope.unfrozenInvalidClose = function () {
                        $scope.unfrozenInvalidPopup.close();
                    }

                    //提交整个质押单，包含所有质押明细
                    $scope.UnfrozenSubmit = function () {
                        executeOperationRequest('submit');
                    }

                    //撤销质检单
                    $scope.repealed = function () {
                        executeOperationRequest('repealed');
                    }

                    //审核通过质检单
                    $scope.audit = function () {
                        executeOperationRequest('audit');
                    }



                    //执行请求
                    var executeOperationRequest = function (type) {
                        var selectData = WMS.GRIDUTILS.getCustomSelectedData($scope.unfrozenGrid);
                        if(selectData.length === 0){
                            kendo.ui.ExtAlertDialog.showError("请至少选择一条数据!");
                            $scope.unfrozenGrid.dataSource.read();
                            return;
                        };
                        var id = selectData[0].id;
                        var url,method;
                        if ("submit" === type){
                            url = unfrozenUrl +"/submit/"+id;
                            method="PUT";
                        }
                        if("repealed" == type){
                            url = unfrozenUrl+"/repealed/"+id;
                            method="PUT";
                        }
                        if("audit" === type){
                            url = unfrozenUrl+"/audit/"+id;
                            method = "PUT";
                        }
                        $sync(url,method).then(function (xhr) {
                            $scope.unfrozenGrid.dataSource.read();
                        },function (xhr) {
                            $scope.unfrozenGrid.dataSource.read();
                        })
                    }

                });


                $scope.$on("kendoWidgetCreated", function (event, widget) {
                    if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                        $scope.unfrozenDetailGrid = widget;
                        widget.bind("edit", function (e) {
                            $scope.editModel = e.model;
                        });
                    }

                    //解押弹窗
                    $scope.unfrozen = function (dataItem) {
                        $scope.unfrozenPopup.refresh().open().center();
                        $scope.unfrozenDetailModel ={};
                        $scope.unfrozenDetailModel.id = dataItem.id;
                        $scope.unfrozenDetailModel.skuName = dataItem.skuName;
                        $scope.unfrozenDetailModel.frozenQtyByNow = dataItem.frozenedQty-dataItem.unfrozenedQty;
                    }
                    //解押确定按钮
                    $scope.unfrozenConfirm = function (e) {
                        var unfrozenDetailId = $scope.unfrozenDetailModel.id;
                        var factoringQty = $scope.unfrozenDetailModel.factoringQty;
                        if(factoringQty <=0 ){
                            kendo.ui.ExtAlertDialog.showError("解押数量必须大于0!");
                            $scope.unfrozenGrid.dataSource.read();
                            return;
                        }
                        if (factoringQty > $scope.unfrozenDetailModel.frozenQtyByNow){
                            kendo.ui.ExtAlertDialog.showError("解押数量不能大于当前质押数量!");
                            return;
                        }
                        var url=unfrozenUrl+"/detail/submit/"+unfrozenDetailId;
                        var params = {
                            unfrozenDetailId:unfrozenDetailId,
                            factoringQty:factoringQty
                        };
                        $sync(url,"PUT",{data:params})
                            .then(function (xhr) {
                                $scope.unfrozenPopup.close();
                                $scope.unfrozenGrid.dataSource.read();
                            },function (xhr) {
                            $scope.unfrozenGrid.dataSource.read();
                            $scope.unfrozenPopup.close();
                        })
                    }
                    //取消窗口关闭
                    $scope.unfrozenClose = function () {
                        $scope.unfrozenPopup.close();
                    }
                });


            }
    ]);
})