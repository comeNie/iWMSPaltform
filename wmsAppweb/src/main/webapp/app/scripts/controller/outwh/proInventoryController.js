define(['scripts/controller/controller', '../../model/inwh/proInventoryModel'], function (controller, proInventory) {
    "use strict";
    controller.controller('proInventoryController',['$scope', '$rootScope', 'sync', 'url','wmsLog', 'wmsDataSource', '$filter',
        function($scope, $rootScope, $sync, $url, wmsLog, wmsDataSource, $filter) {
            $scope.list = [];
            var proInventoryUrl = $url.outwhProInventoryUrl,
                proInventoryColumns = [
                    { field: 'id', title: '加工单号', filterable: false, align: 'left', width: '100px'},
                    { field: 'statusCode', title: '单据状态', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { field: 'cargoOwnerId', title: '货主', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '150px'},
                    { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '150px'},
                    { field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '150px'},
                    { field: 'qty', title: '加工数量', filterable: false, align: 'left', width: '100px'},
                    { field: 'storageRoomId', title: '库房', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.storageRoomFormat("storageRoomId", "storageRoomSrc")},
                    { field: 'workGroupNo', title: '批次号', filterable: false, align: 'left', width: '100px'},

                ],
                proInventoryDetailColumns = [
                    { field: 'cargoOwnerId', title: '货主', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                    { field: 'statusCode', title: '单据状态', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
                    { field: 'skuName', title: '商品名称', filterable: false, align: 'left', width: '150px'},
                    { field: 'sku', title: '商品编码', filterable: false, align: 'left', width: '150px'},
                    { field: 'spec', title: '商品规格', filterable: false, align: 'left', width: '100px'},
                    { field: 'qty', title: '商品数量', filterable: false, align: 'left', width: '100px'},
                    { field: 'storageRoomId', title: '库房', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.storageRoomFormat("storageRoomId", "storageRoomSrc")},
                    { field: 'workGroupNo', title: '批次号', filterable: false, align: 'left', width: '100px'},

                ],
                proInventoryDataSource = wmsDataSource({
                    url:proInventoryUrl,
                    schema: {
                        model: proInventory.proInventory
                    },
                    parse: function (data) {
                        return _.map(data, function (record) {
                            record.activeQty = record.onhandQty - record.allocatedQty-record.mortgagedQty;
                            return record;
                        });
                    },
                    parseRequestData: function(data,method) {
                        if (method === "create") {
                            var proArray = [];
                            var tab = document.getElementById("proTable");
                            var tabLength = tab.rows.length;
                            for (var i = 1 ; i < tabLength ;i ++){
                                var proData = {};
                                proData.proSkuId = tab.rows[i].cells[7].innerHTML;
                                proData.proSku = tab.rows[i].cells[2].innerHTML;
                                proData.proSpec = tab.rows[i].cells[3].innerHTML;
                                proData.proUnitType = tab.rows[i].cells[8].innerHTML;
                                proData.proQty  = $("#proTable tbody tr:eq("+(i-1)+") input:last").val();
                                proData.proStorageRoomId = $("#proTable tbody tr:eq("+(i-1)+") select:first").val();
                                proArray.push(proData);
                            }
                            var dataParam = data;
                                data = {
                                header: dataParam,
                                detailList:proArray
                            }

                        }
                        return data;
                    },
                    save: function(e) {
                        var model = e.model,dataItem = e.sender.$angular_scope.dataItem;
                        if (!_.isEmpty(model)) {
                            $.when(kendo.ui.ExtOkCancelDialog.show({
                                title: "确认/取消",
                                message: "即将对原料进行库内加工，请确认？",
                                icon: "k-ext-question" })
                            ).done(function (response) {
                            });
                        }
                    }
                });

            proInventoryColumns = proInventoryColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            proInventoryDetailColumns = proInventoryDetailColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            proInventoryColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;


            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: proInventoryDataSource,
                toolbar: [
                    { name: "create", text: "新增", className:'btn-auth-add'},
                    { name: "audit",template:'<a class="k-button k-button-audit " ng-click="audit()">审核</a>', className:'btn-auth-audit'},
                    { name: "revoke",template:'<a class="k-button k-button-revoke " ng-click="revoke()">作废</a>', className:'btn-auth-revoke'},
                ],
                columns: proInventoryColumns,
                widgetId:"header",
                editable: {
                    mode: "popup",
                    window:{
                        width:"850"
                    },
                    template: kendo.template($("#proInventory-kendo-template").html())
                },
                customChange:function (grid) {
                    $(".k-grid-finish").hide();
                    var selected = WMS.GRIDUTILS.getCustomSelectedData($scope.proInventoryGrid);
                    if (selected.length > 1) {
                        kendo.ui.ExtAlertDialog.showError(" 最多只能选择一条数据!");
                        $scope.dnHeaderGrid.dataSource.read();
                        return;
                    }
                    if (selected.length === 1) {
                        var data = selected[0];
                        if (data.statusCode !== 'Submitted') {
                            $(".k-button-audit").hide();
                            $(".k-button-revoke").hide();
                        }else{
                            $(".k-button-audit").show();
                            $(".k-button-revoke").show();
                        }
                    }else {
                        $(".k-button-audit").show();
                        $(".k-button-revoke").show();
                    }
                }
            }, $scope);


            $scope.proInventoryDetailGridOptions = function (dataItem) {

                var defaultOptions = {
                    dataSource: wmsDataSource({
                        url: proInventoryUrl + "/" + dataItem.id+ "/detail",
                        schema: {
                            model: proInventory.proInventorydetail
                        }
                    }),
                    columns: proInventoryDetailColumns,
                    widgetId:"detail",
                };

                return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
            };

            //操作日志
            $scope.logOptions = wmsLog.operationLog;
            $scope.$on("kendoWidgetCreated", function (event, widget) {
                if (widget.options !== undefined && widget.options.widgetId === 'header'){
                    $scope.proInventoryGrid = widget;
                    widget.bind("edit", function (e){
                        $scope.editModel = e.model;
                    });
                }

                //库存商品选择
                $scope.windowOpen = function (parentGrid) {
                    $scope.inventoryPopup.refresh().open().center();
                    $scope.inventoryPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        //赋值
                        $scope.editModel.set("inventoryId", resultData.id);
                        $scope.editModel.set("storageRoomId", resultData.storageRoomId);
                        $scope.editModel.set("skuName", resultData.skuName);
                        $scope.editModel.set("skuId", resultData.skuId);
                        $scope.editModel.set("cargoOwnerId", resultData.cargoOwnerId);
                        $scope.editModel.set("spec", resultData.spec);
                        $scope.editModel.set("oldQty",resultData.activeQty);
                        $scope.editModel.set("unitType",resultData.unitType);
                        $scope.editModel.set("sku",resultData.sku);
                        $scope.editModel.set("cargoOwnerId",resultData.cargoOwnerId);
                    };
                }


                $scope.audit = function () {
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.proInventoryGrid);
                    if(selectedData.length === 0){
                        kendo.ui.ExtAlertDialog.showError("请先选择一条数据！");
                        return;
                    }
                    if(selectedData.length > 1){
                        kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据!");
                        return ;
                    }
                    var id = selectedData[0].id;
                    var url = proInventoryUrl + "/audit/" + id;
                    var method = "PUT";
                    $sync(url,method).then(function (xhr) {
                        $scope.proInventoryGrid.dataSource.read();
                    },function (xhr) {
                        $scope.proInventoryGrid.dataSource.read();
                    })
                };
                $scope.revoke = function () {
                    var selectedData = WMS.GRIDUTILS.getCustomSelectedData($scope.proInventoryGrid);
                    if(selectedData.length === 0){
                        kendo.ui.ExtAlertDialog.showError("请先选择一条数据");
                        return;
                    }
                    if(selectedData.length > 1){
                        kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据");
                        return;
                    }
                    var id = selectedData[0].id;
                    var url = proInventoryUrl + "/revoke/" + id;
                    var method = "PUT";
                    $sync(url,method).then(function (xhr) {
                        $scope.proInventoryGrid.dataSource.read();
                    },function (xhr) {
                        $scope.proInventoryGrid.dataSource.read();
                    })
                };

            });


            $scope.$on("kendoWidgetCreated", function (event, widget) {

                //skuIdArr已添加的要加工商品Id集合
                var skuIdArr = new Array();
                //动态添加商品
                $scope.addRow = function (dataItem) {
                    if(dataItem.sku === null || dataItem.sku === ""){
                        kendo.ui.ExtAlertDialog.showError("请先选择要加工的商品信息!");
                        return;
                    }
                    $scope.skuProcessPopup.initParam = function (subScope) {
                        subScope.param = dataItem.sku;
                    };
                    $scope.skuProcessPopup.refresh().open().center();
                    $scope.skuProcessPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)) {
                            return;
                        }
                        if(resultData.id === dataItem.skuId){
                            kendo.ui.ExtAlertDialog.showError("请选择与加工的商品不同的商品!");
                            return;
                        };
                        var proTable = document.getElementById("proTable");
                        //skuIdArr赋值
                        for(var i = 1 ; i < proTable.rows.length ; i ++ ){
                            skuIdArr.push(proTable.rows[i].cells[7].innerHTML);
                        }
                        for (var i = 0 ; i < skuIdArr.length ; i ++){
                            if(skuIdArr[i].toString() === resultData.id.toString()){
                                kendo.ui.ExtAlertDialog.showError("该商品已选择,请选择其它商品!");
                                return;
                            }
                        }
                        skuIdArr.push(resultData.id);
                        $sync(window.BASEPATH+"/index/storageRoom/warehouseId", "GET")
                            .then(function (xhr) {
                                var inventoryList = xhr.result;
                                var option = null;
                                for(var i = 0 ; i < inventoryList.length ; i ++){
                                    if(dataItem.storageRoomId === inventoryList[i].value){
                                        if(option === null){
                                            option = "<option value='"+inventoryList[i].value+"'selected='selected'>"+inventoryList[i].key+"</option>";
                                        }else{
                                            option = option + "<option value='"+inventoryList[i].value+"'selected='selected'>"+inventoryList[i].key+"</option>";
                                        }
                                    }else{
                                        if(option === null){
                                            option ="<option value='"+inventoryList[i].value+"'>"+inventoryList[i].key+"</option>";
                                        }else{
                                            option = option + "<option value='"+inventoryList[i].value+"'>"+inventoryList[i].key+"</option>";
                                        }

                                    }
                                }
                                var limit = "onchange='if(this.value != null){this.value=parseInt(this.value)<1?1:this.value}else{this.value=1}'";
                                var inventory = "<select>" + option + "</select>";
                                var tr = "<tr>" +
                                    "<td>" +"<input name='checkBox' type='checkbox'/>"+"</td>"+
                                    "<td>"+resultData.itemName+"</td>" +
                                    "<td>"+resultData.sku+"</td>" +
                                    "<td>"+resultData.spec+"</td>" +
                                    "<td>"+$filter('codeFormat')(resultData.unitType,'MasterUnit')+"</td>" +
                                    "<td>"+inventory+"</td>" +
                                    "<td>"+"<input type='number'"+limit+" value='1' required/>"+"</td>" +
                                    "<td hidden='hidden'>"+resultData.id+"</td>" +
                                    "<td hidden='hidden'>"+resultData.unitType+"</td>" +
                                    "</tr>";
                                $("#proTable"+" tbody").append(tr);
                            });
                    };
                };

                //复选框选中全部
                $scope.chooseAll = function (dataItem) {
                    var chooseAll = document.getElementById("chooseAll");
                    $("#proTable :checkbox").each(function (key,value) {
                        if(value.name === "checkBox"){
                            if(chooseAll.checked){
                                value.checked = true;
                            }else {
                                value.checked = false;
                            }
                        }
                    });
                }

                //删除分拣的商品
                $scope.deleteRows = function (dataItem) {
                    if(dataItem.sku === null || dataItem.sku === ""){
                        kendo.ui.ExtAlertDialog.showError("请先选择要加工的商品信息!");
                        return;
                    }
                    $("#proTable :checkbox").each(function (key,value) {
                        if(value.name === "checkBox"){
                            if($(value).prop('checked')){
                                if(value.id !== "chooseAll"){
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


