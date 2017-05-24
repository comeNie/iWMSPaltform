define(['../controller', '../../model/inwh/proInvPackageModel'], function (controller, proInvPackage) {
    "use strict";
    controller.controller('proInvPackageController', ['$scope', '$rootScope', 'sync', 'url', 'wmsLog', 'wmsDataSource', '$filter',
        function ($scope, $rootScope, $sync, $url, wmsLog, wmsDataSource, $filter) {
            $scope.list = [];
            var proInvPackageUrl = $url.inventoryProInvPackageUrl,
                proInvPackageColumns = [
                    {field: 'id', title: '加工单号', filterable: false, align: 'left', width: '100px'},
                    {field: 'statusCode', title: '单据状态', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.codeFormat('statusCode', 'TicketStatus')},
                    {field: 'cargoOwnerId', title: '货主', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    {field: 'workGroupNo', title: '批次号', filterable: false, align: 'left', width: '150px'},
                    {field: 'typeCode', title: '单据类型', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('typeCode','HandleType')},

                ],
                proInvPackageDetailColumns = [
                    {field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '150px'},
                    {field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '150px'},
                    {field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '100px'},
                    {field: 'unitType', title: '商品单位', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    {field: 'typeCode', title: '数据类型', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('typeCode','MachiningType')},
                    {field: 'invPackageQty', title: '商品数量', filterable: false, align: 'left', width: '100px'},
                    {field: 'storageRoomId', title: '库房', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.storageRoomFormat("storageRoomId", "storageRoomSrc")},

                ],
                proInvPackageDataSource = wmsDataSource({
                    url: proInvPackageUrl,
                    schema: {
                        model: proInvPackage.proInvPackage
                    },
                    parse: function (data) {
                        return _.map(data, function (record) {
                            record.activeQty = record.onhandQty - record.allocatedQty - record.mortgagedQty;
                            return record;
                        });
                    },
                    parseRequestData: function (data, method) {
                        if (method === "create") {
                            var oldProTableArray = [];
                            var oldFuTableArray = [];
                            var newProTableArray = [];
                            var newFuTableArray = [];
                            var oldProTable = document.getElementById("oldProTable");
                            var oldFuTable = document.getElementById("oldFuTable");
                            var newProTable = document.getElementById("newProTable");
                            var newFuTable = document.getElementById("newFuTable");
                            var tabLength = oldProTable.rows.length;
                            for (var i = 1; i < tabLength; i++) {
                                var table = {};
                                table.invPackageId = oldProTable.rows[i].cells[7].innerHTML;
                                table.proSku = oldProTable.rows[i].cells[2].innerHTML;
                                table.spec = oldProTable.rows[i].cells[3].innerHTML;
                                table.unitType = oldProTable.rows[i].cells[8].innerHTML;
                                table.invPackageQty = $("#oldProTable tbody tr:eq(" + (i - 1) + ") input:last").val();
                                table.storageRoomId = $("#oldProTable tbody tr:eq(" + (i - 1) + ") select:first").val();
                                oldProTableArray.push(table);
                            }
                            tabLength = oldFuTable.rows.length;
                            for (var i = 1; i < tabLength; i++) {
                                var table = {};
                                table.invPackageId = oldFuTable.rows[i].cells[7].innerHTML;
                                table.proSku = oldFuTable.rows[i].cells[2].innerHTML;
                                table.spec = oldFuTable.rows[i].cells[3].innerHTML;
                                table.unitType = oldFuTable.rows[i].cells[8].innerHTML;
                                table.invPackageQty = $("#oldFuTable tbody tr:eq(" + (i - 1) + ") input:last").val();
                                table.storageRoomId = $("#oldFuTable tbody tr:eq(" + (i - 1) + ") select:first").val();
                                oldFuTableArray.push(table);
                            }
                            tabLength = newProTable.rows.length;
                            for (var i = 1; i < tabLength; i++) {
                                var table = {};
                                table.invPackageId = newProTable.rows[i].cells[7].innerHTML;
                                table.proSku = newProTable.rows[i].cells[2].innerHTML;
                                table.spec = newProTable.rows[i].cells[3].innerHTML;
                                table.unitType = newProTable.rows[i].cells[8].innerHTML;
                                table.invPackageQty = $("#newProTable tbody tr:eq(" + (i - 1) + ") input:last").val();
                                table.storageRoomId = $("#newProTable tbody tr:eq(" + (i - 1) + ") select:first").val();
                                newProTableArray.push(table);
                            }
                            tabLength = newFuTable.rows.length;
                            for (var i = 1; i < tabLength; i++) {
                                var oldProTable = {};
                                table.invPackageId = newFuTable.rows[i].cells[7].innerHTML;
                                table.proSku = newFuTable.rows[i].cells[2].innerHTML;
                                table.spec = newFuTable.rows[i].cells[3].innerHTML;
                                table.unitType = newFuTable.rows[i].cells[8].innerHTML;
                                table.invPackageQty = $("#newFuTable tbody tr:eq(" + (i - 1) + ") input:last").val();
                                table.storageRoomId = $("#newFuTable tbody tr:eq(" + (i - 1) + ") select:first").val();
                                newFuTableArray.push(table);
                            }
                            var dataParam = data;
                            data = {
                                header: dataParam,
                                oldProTableArray: oldProTableArray,
                                oldFuTableArray: oldFuTableArray,
                                newProTableArray: newProTableArray,
                                newFuTableArray: newFuTableArray
                            }

                        }
                        return data;
                    },
                    save: function (e) {
                        var model = e.model, dataItem = e.sender.$angular_scope.dataItem;
                        if (!_.isEmpty(model)) {
                            $.when(kendo.ui.ExtOkCancelDialog.show({
                                    title: "确认/取消",
                                    message: "即将对原料进行库内加工，请确认？",
                                    icon: "k-ext-question"
                                })
                            ).done(function (response) {
                            });
                        }
                    }
                });

            proInvPackageColumns = proInvPackageColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            proInvPackageDetailColumns = proInvPackageDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            proInvPackageColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;


            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: proInvPackageDataSource,
                toolbar: [
                    {name: "create", text: "新增", className: 'btn-auth-add'},
                    {
                        name: "reviewed",
                        template: '<a class="k-button k-button-reviewed " ng-click="reviewed()">审核</a>',
                        className: 'btn-auth-reviewed'
                    },
                    {
                        name: "invalid",
                        template: '<a class="k-button k-button-reviewed " ng-click="invalid()">作废</a>',
                        className: 'btn-auth-invalid'
                    },
                ],
                columns: proInvPackageColumns,
                widgetId: "header",
                editable: {
                    mode: "popup",
                    window: {
                        width: window.innerWidth,
                        height: window.innerHeight -50,
                    },
                    template: kendo.template($("#proInvPackage-kendo-template").html())
                },
                customChange: function (grid) {
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.proInvPackageGrid);
                    if (selected.length > 1) {
                        kendo.ui.ExtAlertDialog.showError(" 最多只能选择一条数据!");
                        $scope.dnHeaderGrid.dataSource.read();
                        return;
                    }
                    if (selected.length === 1) {
                        var data = selected[0];
                        if (data.statusCode !== 'Initial') {
                            $(".k-button-reviewed").hide();
                            $(".k-button-invalid").hide();
                        } else {
                            $(".k-button-reviewed").show();
                            $(".k-button-invalid").show();
                        }
                    } else {
                        $(".k-button-reviewed").show();
                        $(".k-button-invalid").show();
                    }
                }
            }, $scope);


            $scope.proInvPackageDetailGridOptionsOut = function (dataItem) {

                var defaultOptions = {
                    dataSource: wmsDataSource({
                        url: proInvPackageUrl + "/" + dataItem.id + "/outputDetail",
                        schema: {
                            model: proInvPackage.proInvPackageDetail
                        }
                    }),
                    columns: proInvPackageDetailColumns,
                    widgetId: "detail",
                };

                return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
            };
            $scope.proInvPackageDetailGridOptionsConsume = function (dataItem) {

                var defaultOptions = {
                    dataSource: wmsDataSource({
                        url: proInvPackageUrl + "/" + dataItem.id + "/consumeDetail",
                        schema: {
                            model: proInvPackage.proInvPackageDetail
                        }
                    }),
                    columns: proInvPackageDetailColumns,
                    widgetId: "detail",
                };

                return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
            };

            //操作日志
            $scope.logOptions = wmsLog.operationLog;
            $scope.$on("kendoWidgetCreated", function (event, widget) {
                if (widget.options !== undefined && widget.options.widgetId === 'header') {
                    $scope.proInvPackageGrid = widget;
                    widget.bind("edit", function (e) {
                        $scope.editModel = e.model;
                    });
                }


                $scope.reviewed = function () {
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.proInvPackageGrid);
                    if (selectedData.length === 0) {
                        kendo.ui.ExtAlertDialog.showError("请先选择一条数据！");
                        return;
                    }
                    if (selectedData.length > 1) {
                        kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据!");
                        return;
                    }
                    var id = selectedData[0].id;
                    var url = proInvPackageUrl + "/reviewed/" + id;
                    var method = "PUT";
                    $sync(url, method).then(function (xhr) {
                        $scope.proInvPackageGrid.dataSource.read();
                    }, function (xhr) {
                        $scope.proInvPackageGrid.dataSource.read();
                    })
                };
                $scope.invalid = function () {
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.proInvPackageGrid);
                    if (selectedData.length === 0) {
                        kendo.ui.ExtAlertDialog.showError("请先选择一条数据");
                        return;
                    }
                    if (selectedData.length > 1) {
                        kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据");
                        return;
                    }
                    var id = selectedData[0].id;
                    var url = proInvPackageUrl + "/invalid/" + id;
                    var method = "PUT";
                    $sync(url, method).then(function (xhr) {
                        $scope.proInvPackageGrid.dataSource.read();
                    }, function (xhr) {
                        $scope.proInvPackageGrid.dataSource.read();
                    })
                };

            });


            $scope.$on("kendoWidgetCreated", function (event, widget) {

                //oldProTable添加商品
                $scope.oldProTableAddRow = function (dataItem) {
                    addRow(dataItem, 'oldProTable','oldFuTable','old');
                }
                //oldProTable删除商品
                $scope.oldProTableDeleteRows = function (dataItem) {
                    deleteRows(dataItem, 'oldProTable');
                }
                //oldProTable选择所有商品
                $scope.oldProTableChooseAll = function (dataItem) {
                    chooseAll(dataItem, 'oldProTable');
                }
                //oldFuTable添加商品
                $scope.oldFuTableAddFuRow = function (dataItem) {
                    addRow(dataItem, 'oldFuTable','oldProTable','old');
                }
                //oldFuTable删除商品
                $scope.oldFuTableDeleteFuRows = function (dataItem) {
                    deleteRows(dataItem, 'oldFuTable');
                }
                //oldFuTable选择所有商品
                $scope.oldFuTableFuChooseAll = function (dataItem) {
                    chooseAll(dataItem, 'oldFuTable');
                }
                //newProTable添加商品
                $scope.newProTableAddRow = function (dataItem) {
                    addRow(dataItem, 'newProTable','newFuTable','new');
                }
                //newProTable删除商品
                $scope.newProTableDeleteRows = function (dataItem) {
                    deleteRows(dataItem, 'newProTable');
                }
                //newProTable选择所有商品
                $scope.newProTableChooseAll = function (dataItem) {
                    chooseAll(dataItem, 'newProTable');
                }
                //newProTable添加商品
                $scope.newFuTableAddFuRow = function (dataItem) {
                    addRow(dataItem, 'newFuTable','newProTable','new');
                }
                //newProTable删除商品
                $scope.newFuTableDeleteFuRows = function (dataItem) {
                    deleteRows(dataItem, 'newFuTable');
                }
                //newProTable选择所有商品
                $scope.newFuTableChooseAll = function (dataItem) {
                    chooseAll(dataItem, 'newFuTable');
                }

                //添加商品行
                function addRow(dataItem, tableId,aboutTableId, type) {
                    //skuIdArr已添加的要加工商品Id集合
                    var skuIdArr = new Array();
                    if (dataItem.cargoOwnerId === null || dataItem.cargoOwnerId === 0) {
                        kendo.ui.ExtAlertDialog.showError("请先选择货主!");
                        return;
                    }
                    if(type === 'new'){
                        $scope.skuPopup.initParam = function (subScope) {
                            subScope.param = 0;
                        };
                        $scope.skuPopup.refresh().open().center();
                        $scope.skuPopup.setReturnData = function (resultData) {
                            add(dataItem, tableId,aboutTableId, type,resultData,skuIdArr);
                        };
                    }else{
                        $scope.inventoryCargoOwnerPopup.initParam = function (subScope) {
                            subScope.param = dataItem.cargoOwnerId;
                        };
                        $scope.inventoryCargoOwnerPopup.refresh().open().center();
                        $scope.inventoryCargoOwnerPopup.setReturnData = function (resultData) {
                            if(resultData.activeQty<1){
                                kendo.ui.ExtAlertDialog.showError("当前仓库该商品无可用数量!");
                                return;
                            }
                            add(dataItem, tableId,aboutTableId, type,resultData,skuIdArr);
                        };
                    }
                };
                function add(dataItem, tableId, aboutTableId,type,resultData,skuIdArr) {
                    if (_.isEmpty(resultData)) {
                        return;
                    }
                    if (resultData.id === dataItem.skuId) {
                        kendo.ui.ExtAlertDialog.showError("请选择与加工的商品不同的商品!");
                        return;
                    };
                    var proTable = document.getElementById(tableId);
                    //skuIdArr赋值
                    for (var i = 1; i < proTable.rows.length; i++) {
                        skuIdArr.push(proTable.rows[i].cells[7].innerHTML);
                    }
                    var skuId;
                    if(type === 'new'){
                        skuId = resultData.id.toString()
                    }else{
                        skuId = resultData.skuId.toString();
                    }
                    for (var i = 0; i < skuIdArr.length; i++) {
                        if (skuIdArr[i].toString() === skuId) {
                            kendo.ui.ExtAlertDialog.showError("该商品已选择,请选择其它商品!");
                            return;
                        }
                    }
                    proTable = document.getElementById(aboutTableId);
                    //skuIdArr赋值
                    for (var i = 1; i < proTable.rows.length; i++) {
                        skuIdArr.push(proTable.rows[i].cells[7].innerHTML);
                    }
                    for (var i = 0; i < skuIdArr.length; i++) {
                        if (skuIdArr[i].toString() === skuId) {
                            var message = "该商品在辅料已选择,请选择其它商品!";
                            if(tableId !== 'oldProTable' && tableId !== 'newProTable'){
                                message = "该商品在商品中已选择,请选择其它商品!";
                            }
                            kendo.ui.ExtAlertDialog.showError(message);
                            return;
                        }
                    }
                    $sync(window.BASEPATH + "/index/storageRoom/warehouseId", "GET")
                        .then(function (xhr) {
                            var inventoryList = xhr.result;
                            var option = null;
                            for (var i = 0; i < inventoryList.length; i++) {
                                if (resultData.storageRoomId === inventoryList[i].value) {
                                    if (option === null) {
                                        option = "<option value='" + inventoryList[i].value + "'selected='selected'>" + inventoryList[i].key + "</option>";
                                    } else {
                                        option = option + "<option value='" + inventoryList[i].value + "'selected='selected'>" + inventoryList[i].key + "</option>";
                                    }
                                } else {
                                    if (option === null) {
                                        option = "<option value='" + inventoryList[i].value + "'>" + inventoryList[i].key + "</option>";
                                    } else {
                                        option = option + "<option value='" + inventoryList[i].value + "'>" + inventoryList[i].key + "</option>";
                                    }

                                }
                            }
                            var limit = "onchange='if(this.value != null){this.value=parseInt(this.value)<1?1:this.value}else{this.value=1}'";
                            var inventory;
                            if(type !== 'new'){
                                inventory = "<select disabled='disabled'>" + option + "</select>";
                                var tr = "<tr>" +
                                    "<td>" + "<input name='checkBox' type='checkbox'/>" + "</td>" +
                                    "<td>" + resultData.skuName + "</td>" +
                                    "<td>" + resultData.sku + "</td>" +
                                    "<td>" + resultData.spec + "</td>" +
                                    "<td>" + $filter('codeFormat')(resultData.unitType, 'MasterUnit') + "</td>" +
                                    "<td>" + inventory + "</td>" +
                                    "<td>" + "<input type='number'" + limit + " value='1' max='"+resultData.activeQty+"' required/>" + "</td>" +
                                    "<td hidden='hidden'>" + resultData.skuId + "</td>" +
                                    "<td hidden='hidden'>" + resultData.unitType + "</td>" +
                                    "<td >" + resultData.activeQty + "</td>" +
                                    "</tr>";
                            }else{
                                inventory = "<select>" + option + "</select>";
                                var tr = "<tr>" +
                                    "<td>" + "<input name='checkBox' type='checkbox'/>" + "</td>" +
                                    "<td>" + resultData.itemName + "</td>" +
                                    "<td>" + resultData.sku + "</td>" +
                                    "<td>" + resultData.spec + "</td>" +
                                    "<td>" + $filter('codeFormat')(resultData.unitType, 'MasterUnit') + "</td>" +
                                    "<td>" + inventory + "</td>" +
                                    "<td>" + "<input type='number'" + limit + " value='1' required/>" + "</td>" +
                                    "<td hidden='hidden'>" + resultData.id + "</td>" +
                                    "<td hidden='hidden'>" + resultData.unitType + "</td>" +
                                    "</tr>";
                            }

                            $("#" + tableId + " tbody").append(tr);
                        });
                }

                //复选框选中全部
                function chooseAll(dataItem, tableId) {
                    var chooseAll = document.getElementById(tableId+"ChooseAll");
                    $("#" + tableId + " :checkbox").each(function (key, value) {
                        if (value.name === "checkBox") {
                            if (chooseAll.checked) {
                                value.checked = true;
                            } else {
                                value.checked = false;
                            }
                        }
                    });
                }

                //删除分拣的商品
                function deleteRows(dataItem, tableId) {
                    $("#" + tableId + " :checkbox").each(function (key, value) {
                        if (value.name === "checkBox") {
                            if ($(value).prop('checked')) {
                                if (value.id !== "chooseAll") {
                                    var tr = value.parentNode.parentNode;
                                    tr.parentNode.removeChild(tr);
                                }
                            }
                        }
                    });
                };


            });
        }]);
})


