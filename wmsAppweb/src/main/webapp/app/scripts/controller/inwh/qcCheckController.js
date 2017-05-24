define(['scripts/controller/controller', '../../model/inwh/qcCheckModel'], function (controller, qcCheck) {
    "use strict";
    controller.controller('qcCheckController', ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function ($scope, $rootScope, $sync, $url, wmsDataSource) {

            var qcCheckHeaderUrl = $url.inwhqcCheckUrl,
                qcCheckHeaderColumns = [
                    // WMS.GRIDUTILS.CommonOptionButton(),
                    {title: '质检单号', field: 'id', align: 'left', width: "100px"},
                    {
                        title: '质检单据类型',
                        field: 'typeCode',
                        align: 'left',
                        width: "100px",
                        template: WMS.UTILS.codeFormat("typeCode", 'OrderType')
                    },
                    {title: '入库单号', align: 'left', field: 'qcId', width: "100px"},
                    {
                        title: '状态',
                        align: 'left',
                        field: 'statusCode',
                        width: "120px",
                        template: WMS.UTILS.codeFormat("statusCode", 'QualityStatus')
                    },
                ],
                qcCheckDetailColumns = [
                    // WMS.GRIDUTILS.CommonOptionButton(),
                    {title: '质检明细单号', field: 'id', align: 'left', width: "120px"},
                    {title: '入库明细单号', align: 'left', field: 'qcDetailId', width: "120px"},
                    {title: '质检商品', align: 'left', field: 'skuName', width: "150px"},
                    {title: '商品编码', align: 'left', field: 'sku', width: "150px"},
                    {title: '商品条码', align: 'left', field: 'skuBarcode', width: "150px"},
                    {
                        title: '合格标志',
                        align: 'left',
                        field: 'isQualified',
                        width: "100px",
                        template: WMS.UTILS.checkboxDisabledTmp("isQualified")
                    },
                    {title: '不合格原因', align: 'left', field: 'unQualifiedReason', width: "150px"},
                    {title: '备注', align: 'left', field: 'description', width: "120px"},
                ],
                qcCheckHeaderDataSource = wmsDataSource({
                    url: qcCheckHeaderUrl,
                    schema: {
                        model: qcCheck.qcCheck
                    }
                });
            qcCheckHeaderColumns = qcCheckHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            qcCheckHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            qcCheckDetailColumns = qcCheckDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            qcCheckDetailColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: qcCheckHeaderDataSource,
                toolbar: [
                    // { template:'<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"},
                    {
                        name: "submit",
                        template: '<a class="k-button k-button-icontext k-grid-submit" ng-click="submit()" href="\\#"><span class="k-icon k-submit"></span>提交</a>',
                        className: "btn-auth-submit"
                    },
                ],
                columns: qcCheckHeaderColumns,
                editable: {
                    mode: "popup",
                    window: {
                        width: "620"
                    },
                    template: kendo.template($("#qcCheck-kendo-template").html())
                },
                widgetId: "header",
                customChange: function (grid) {
                    $(".k-grid-submit").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.qcCheckHeaderGrid);
                    if (selected.length > 1) {
                        kendo.ui.ExtAlertDialog.showError(" 最多只能选择一条数据!");
                        $scope.qcCheckHeaderGrid.dataSource.read();
                        $(".k-grid-submit").show();
                        return;
                    }
                    if (selected.length === 1) {
                        var data = selected[0];
                        if (data.statusCode === 'Initial') {
                            ;
                            $(".k-grid-submit").show();
                        } else {
                            $(".k-grid-submit").hide();
                        }
                    } else {
                        $(".k-grid-submit").show();
                    }
                }
            }, $scope);
            $scope.qcCheckDetailOptions = function (dataItem) {

                var defaultOptions = {
                    dataSource: wmsDataSource({
                        url: qcCheckHeaderUrl + "/" + dataItem.id + "/detail",
                        schema: {
                            model: qcCheck.qcCheckdetail
                        }
                    }),
                    toolbar: [
                        {
                            name: 'reviewed',
                            template: '<a class="k-button k-button-icontext k-grid-reviewed " ng-click="reviewed()" href="\\#"><span class="k-icon k-reviewed "></span>质检审核</a>',
                            className: "btn-auth-reviewed "
                        },
                    ],
                    dataBound: function (e) {
                        var grid = this,
                            trs = grid.tbody.find(">tr");
                        if (dataItem.statusCode !== "Initial") {
                            // grid.element.find(".k-button-reviewed").remove(); //明细toolbar按钮
                            $(".k-grid-reviewed").hide();
                        }
                    },
                    editable: {
                        mode: "popup",
                        window: {
                            width: "300"
                        },
                        // template: kendo.template($("#qcCheckDetail-kendo-template").html())
                    },
                    columns: qcCheckDetailColumns,
                    widgetId: "detail",
                };

                return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
            };
            $scope.$on("kendoWidgetCreated", function (event, widget) {

                if (widget.options !== undefined && widget.options.widgetId === 'header') {
                    $scope.qcCheckHeaderGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }
                ;
                if (widget.options !== undefined && widget.options.widgetId === 'detail') {
                    $scope.qcCheckDetailGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }

                //是否合格方法
                $scope.isQualified = function () {
                    if (!document.getElementById('qcCheckDetailModelIsQualified').checked) {
                        $scope.qcCheckDetailModel.unQualifiedReason = "";
                        $('#qcCheckDetailModelUnQualifiedReason').removeAttr("disabled");
                    } else {
                        $scope.qcCheckDetailModel.unQualifiedReason = "";
                        $('#qcCheckDetailModelUnQualifiedReason').attr("disabled", true);
                    }
                }

                function getCurrentIds() {
                    //获取选中的行
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.qcCheckHeaderGrid);
                    var ids = [];
                    selectedData.forEach(function (data) {
                        ids.push(data.id);
                    });
                    var id = ids.join(",");
                    return id;
                }

                //明细审核提交方法
                $scope.submit = function () {
                    var id = getCurrentIds();
                    var idArr = id.split(",");
                    if (!id || idArr.length > 1) {
                        kendo.ui.ExtAlertDialog.showError("请选择一条数据.");
                        return;
                    }
                    kendo.ui.ExtOkCancelDialog.show({
                        title: "确认",
                        message: "确定要提交质检报告么?",
                        icon: 'k-ext-question'
                    }).then(function (resp) {
                        if (resp.button === 'OK') {
                            $sync(qcCheckHeaderUrl + "/submit/" + id, "GET").then(function (xhr) {
                                $scope.qcCheckHeaderGrid.dataSource.read();
                            }), (function (xhr) {
                                $scope.qcCheckHeaderGrid.dataSource.read();
                            })
                        }
                    });
                }
                //质检明细方法
                $scope.reviewed = function () {
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.qcCheckDetailGrid);
                    if (selectedData.length !== 1) {
                        kendo.ui.ExtAlertDialog.showError("请选择一条数据.");
                        return;
                    }
                    var dataItem = selectedData[0];
                    $scope.qcCheckDetailPopup.refresh().open().center();
                    $('#qcCheckDetailModelUnQualifiedReason').removeAttr("disabled");
                    $scope.qcCheckDetailModel = {};
                    $scope.qcCheckDetailModel.id = dataItem.id;
                    $scope.qcCheckDetailModel.skuName = dataItem.skuName;
                    $scope.qcCheckDetailModel.sku = dataItem.sku;
                    $scope.qcCheckDetailModel.skuBarcode = dataItem.skuBarcode;
                    $scope.qcCheckDetailModel.qcDetailId = dataItem.qcDetailId;
                    $scope.qcCheckDetailModel.isQualified = dataItem.isQualified;
                    $scope.qcCheckDetailModel.unQualifiedReason = dataItem.unQualifiedReason;
                    $scope.qcCheckDetailModel.description = dataItem.description;
                    $scope.qcCheckDetailModel.parentId = dataItem.parentId;
                    if (dataItem.isQualified == 1) {
                        $('#qcCheckDetailModelUnQualifiedReason').attr("disabled", true);
                    }

                }

                $scope.qcCheckConfirm = function (e) {
                    var formValidator = $(e.target).parents(".k-edit-form-container").kendoValidator().data("kendoValidator");
                    if (!formValidator.validate()) {
                        return;
                    }
                    var param = {
                        id: $scope.qcCheckDetailModel.id,
                        skuName: $scope.qcCheckDetailModel.skuName,
                        sku: $scope.qcCheckDetailModel.sku,
                        skuBarcode: $scope.qcCheckDetailModel.skuBarcode,
                        qcDetailId: $scope.qcCheckDetailModel.qcDetailId,
                        isQualified: $scope.qcCheckDetailModel.isQualified,
                        unQualifiedReason: $scope.qcCheckDetailModel.unQualifiedReason,
                        description: $scope.qcCheckDetailModel.description,
                        parentId: $scope.qcCheckDetailModel.parentId
                    };
                    $sync(qcCheckHeaderUrl + "/" + param.parentId + "/detail/" + param.id, "PUT", {data: param})
                        .then(function (xhr) {
                            $scope.qcCheckDetailPopup.close();
                            $scope.qcCheckDetailGrid.dataSource.read();
                        }, function (xhr) {
                            $scope.qcCheckDetailGrid.dataSource.read();
                            $scope.qcCheckDetailPopup.close();
                        })
                }

                $scope.qcCheckClose = function () {
                    $scope.qcCheckDetailPopup.close();
                }
            })

        }
    ]);
})
