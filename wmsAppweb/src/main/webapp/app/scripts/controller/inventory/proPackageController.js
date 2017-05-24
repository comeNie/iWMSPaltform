define(['scripts/controller/controller', '../../model/inwh/proPackageModel'], function (controller, proPackage) {
    "use strict";
    controller.controller('proPackageController',['$scope', '$rootScope', 'sync', 'url', 'wmsLog', 'wmsDataSource', '$filter',
        function($scope, $rootScope, $sync, $url, wmsLog, wmsDataSource, $filter) {
            $scope.list = [];
        var proPackageUrl = $url.inventoryproPackageUrl,
            propackageColumns = [
                {field: 'id',title:'包装单号',filtertable: false,align: 'left',width: '100px'},
                { title: ' 货主', field: 'cargoOwnerId', align: 'left', width: "100px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                { title: ' 库房', field: 'storageRoomId', align: 'left', width: "100px",template:WMS.UTILS.storageRoomFormat("storageRoomId")},
                { title: '商品名称', field: 'skuName',filterable: false, align: 'left', width: '150px'},
                { title: '商品编码', field: 'sku', filterable: false, align: 'left', width: '150px'},
                { title: '包装商品规格', field: 'spec', filterable: false, align: 'left', width: '120px'},
                { title: '包装商品数量',  field: 'qty',filterable: false, align: 'left', width: '120px'},
                { title: '包装批次号', field: 'workGroupNo', filterable: false, align: 'left', width: '100px'},
                { title: '单据状态', field: 'statusCode', filterable: false, align: 'left', width: '100px',template:WMS.UTILS.codeFormat('statusCode','TicketStatus')},
            ],
            proPackageDetailColumns = [
                { title: ' 明细单号', field: 'id', align: 'left', width: "100px"},
                { title: '商品名称', field: 'skuName', align: 'left', width: "100px"},
                { title: '商品编码', field: 'sku', align: 'left', width: "140px"},
                { title: '商品规格', field: 'spec', align: 'left', width: "140px"},
                { title: '商品单位', field: 'unitType', align: 'left', width: "140px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                { title: '商品数量',  field: 'proInventoryQty',filterable: false, align: 'left', width: '100px'},
                { title: '库房', field: 'proStorageRoomId', filterable: false, align: 'left', width: '100px', template: WMS.UTILS.storageRoomFormat("proStorageRoomId", "storageRoomSrc")},
            ],
            proPackageDataSource = wmsDataSource({
                url:proPackageUrl,
                schema:{
                    model:proPackage.proPackage
                },
                parse:function (data) {
                    return _.map(data,function (record) {
                        record.activeQty = record.onhandQty-record.allocatedQty-record.mortgagedQty-record.pickedQty-record.workingQty
                        return record;
                    });
                },
                parseRequestData:function (data,method) {
                    if (method === "create"){
                        var proArray = [];
                        var fuArray = [];
                        var tab = document.getElementById("proTable");
                        var tabLength = tab.rows.length;
                        for(var i = 1; i< tabLength;i++){
                         var proData = {};
                            proData.proInventoryId = tab.rows[i].cells[7].innerHTML;
                            proData.proStorageRoomId = tab.rows[i].cells[10].innerHTML;
                            proData.proInventoryQty  = $("#proTable tbody tr:eq("+(i-1)+") input:last").val();
                            proArray.push(proData);
                        }

                        var futab = document.getElementById("fuTable");
                        var futabLength = futab.rows.length;
                        for(var i = 1; i< futabLength;i++){
                            var fuData = {};
                            fuData.proInventoryId = futab.rows[i].cells[7].innerHTML;
                            fuData.proStorageRoomId = futab.rows[i].cells[10].innerHTML;
                            fuData.proInventoryQty  = $("#fuTable tbody tr:eq("+(i-1)+") input:last").val();
                            fuArray.push(fuData);
                        }
                        var dataParam = data;
                        data = {
                            header: dataParam,
                            detailList:proArray,
                            fuArray:fuArray,

                        }
                    }
                    return data;
                },
                save: function () {
                    var model = e.model,dataItem = e.sender.$angular_scope.dataItem;
                    if(!_.isEmpty(model)){
                        $.when(kendo.ui.ExtAlertDialog.show({
                            title:"确认/取消",
                            message:"即将对商品进行库内包装，请确认？",
                            icon:"k-ext-question"})
                        ).done(function (response) {
                        });
                    }
                }
            });
        propackageColumns = propackageColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
        propackageColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);

        $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;

        //操作日志
        $scope.logOptions = wmsLog.operationLog;

        $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
            dataSource:proPackageDataSource,
            toolbar:[
                {name:"create",text:"新增",className:'btn-auth-add'},
                {name:"reviewed", template: '<a class="k-button k-button-reviewed" ng-click="reviewed(dataItem)">审核</a>',className:'btn-auth-reviewed'},
                {name:"invalid",template:'<a class="k-button k-button-invalid" ng-click="invalid()">作废</a>',className:'btn-auth-invalid'},
            ],
            columns:propackageColumns,
            widgetId:"header",
            editable:{
                mode:"popup",
                window:{
                    height:450,
                    width:"900"
                },
                template:kendo.template($("#proPackage-kendo-template").html())
            },
            customChange:function (grid) {
                $(".k-button-reviewed").hide();
                $(".k-button-invalid").hide();
                var  selected = WMS.GRIDUTILS.getCustomSelectedData($scope.proPackageGrid);
                if (selected.length>1){
                    kendo.ui.ExtAlertDialog.showError("最多只能选择一条数据");
                    $scope.proPackageGrid.dataSource.read();
                    return;
                }
                if(selected.length == 1){
                    var  data = selected[0];
                    if (selected[0].statusCode === 'Submitted'){
                        $(".k-button-reviewed").show();
                        $(".k-button-invalid").show();
                    }else{
                        $(".k-button-reviewed").hide();
                        $(".k-button-invalid").hide();
                    }
                }else {
                    $(".k-button-reviewed").show();
                    $(".k-button-invalid").show();
                }
            }
        },$scope);

        $scope.proPackageDetailOptions = function (dataItem) {

            var defaultOptions = {
                dataSource:wmsDataSource({
                url:proPackageUrl + "/" + dataItem.id+ "/detail",
                    schema:{
                        model:proPackage.proPackageDetail
                    }
                }),
                columns:proPackageDetailColumns,
                widgetId:"detail",
            };
            return WMS.GRIDUTILS.getGridOptions(defaultOptions,$scope);
        };

        $scope.$on("kendoWidgetCreated",function (event,widget) {
            if (widget.options !== undefined && widget.options.widgetId === 'header'){
                $scope.proPackageGrid = widget;
                widget.bind("edit",function (e) {
                    $scope.editModel = e.model;
                });
            }

            //商品礼包选择
            $scope.windowOpen = function (parentGrid) {
                $scope.skuPopup.refresh().open().center();
                $scope.skuPopup.setReturnData = function (resultData) {
                    if(_.isEmpty(resultData)){
                        return;
                    }
                    $scope.editModel.set("skuId", resultData.id);
                    $scope.editModel.set("sku", resultData.sku);
                    $scope.editModel.set("skuName", resultData.itemName);
                    $scope.editModel.set("skuBarcode", resultData.barcode);
                    $scope.editModel.set("spec",resultData.spec);
                    $scope.editModel.set("unitType",resultData.unitType);
                };
            }

            $scope.cargoOwnerIdChange = function (i,m) {

            }

            //审核操作
            $scope.reviewed = function () {
                //获取选择的行
                var selectdeData = WMS.GRIDUTILS.getCustomSelectedData($scope.proPackageGrid);
                if (selectdeData.length === 0){
                    kendo.ui.ExtAlertDialog.showError("请至少选择一条数据");
                    $scope.proPackageGrid.dataSource.read();
                    return;
                }
                var id = selectdeData[0].id;
                $sync(proPackageUrl + "/reviewed/" + id,"PUT").then(function (xhr) {
                  $scope.proPackageGrid.dataSource.read();
                },function (xhr) {
                    $scope.proPackageGrid.dataSource.read();
                })
            };
            //作废操作
            $scope.invalid = function () {
                var selectdeData =WMS.GRIDUTILS.getCustomSelectedData($scope.proPackageGrid);
                if(selectdeData.length === 0){
                    kendo.ui.ExtAlertDialog.showError("请至少选择一条数据");
                    $scope.proPackageGrid.dataSource.read();
                    return;
                }
                var id = selectdeData[0].id;
                $sync(proPackageUrl + "/invalid/" + id,"PUT").then(function (xhr) {
                    $scope.proPackageGrid.dataSource.read();
                },function (xhr) {
                    $scope.proPackageGrid.dataSource.read();
                })
            };


        });

            $scope.$on("kendoWidgetCreated",function (event,widget) {
                //动态添加商品
                $scope.addRow = function (dataItem) {
                    if (dataItem.cargoOwnerId === undefined  ||dataItem.cargoOwnerId === ""  || dataItem.cargoOwnerId === 0){
                        kendo.ui.ExtAlertDialog.showError("请选择货主！");
                            return;
                    }

                    $scope.inventoryCargoOwnerPopup.initParam = function (subScope) {

                        subScope.param = dataItem.cargoOwnerId;
                    };
                    $scope.inventoryCargoOwnerPopup.refresh().open().center();
                    $scope.inventoryCargoOwnerPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)){
                            return;
                        }
                        if(dataItem.skuId === resultData.skuId){
                            kendo.ui.ExtAlertDialog.showError("请选择与包装商品不同的商品!");
                            return;
                        };
                        //商品选择
                        var proTable = document.getElementById("proTable");
                        var proArr = new Array();
                        for(var i =1 ; i<proTable.rows.length;i++){
                            proArr.push(proTable.rows[i].cells[9].innerHTML);
                        }
                        for(var i = 0;i<proArr.length;i++){
                            if (proArr[i] === resultData.skuId.toString()){
                                kendo.ui.ExtAlertDialog.showError("该商品已选择，选重新选择商品");
                                return;
                            }
                        }
                        //商品选择是否和辅料一样
                        var fuTable = document.getElementById("fuTable");
                        var fuCargoOwnerInventoryIdArr = new Array();
                        for(var i = 1 ; i < fuTable.rows.length ; i ++ ){
                            fuCargoOwnerInventoryIdArr.push(fuTable.rows[i].cells[9].innerHTML);
                        }
                        for (var i = 0 ; i < fuCargoOwnerInventoryIdArr.length ; i ++){
                            if(fuCargoOwnerInventoryIdArr[i] === resultData.skuId.toString()){
                                kendo.ui.ExtAlertDialog.showError("该商品已选择,请选择其它商品!");
                                return;
                            }
                        }
                        var limit = "onchange='if(this.value != null){this.value=parseInt(this.value)<1?1:this.value}else{this.value=1}'";
                        var tr = "<tr>"+
                                "<td>"+"<input name='checkBox' type='checkbox'/>"+"</td>"+
                                "<td>"+resultData.skuName+"</td>" +
                                "<td>"+resultData.sku+"</td>"+
                                "<td>"+resultData.spec+"</td>"+
                                "<td>"+$filter('codeFormat')(resultData.unitType,'MasterUnit')+"</td>" +
                                "<td>"+$filter('storageRoomFormat')(resultData.storageRoomId)+"</td>"+
                                "<td>"+"<input type='number'"+limit+" value='1' required/>"+"</td>" +
                                "<td hidden='hidden'>"+resultData.id+"</td>" +
                                "<td hidden='hidden'>"+resultData.unitType+"</td>" +
                                "<td hidden='hidden'>"+resultData.skuId+"</td>" +
                                "<td hidden='hidden'>"+resultData.storageRoomId+"</td>" +
                                "</tr>";
                        $("#proTable"+" tbody").append(tr);
                    };
                };
                //复选框全选
                $scope.chooseAll =function (dataItem) {
                    var chooseAll = document.getElementById("chooseAll");
                    $("#proTable :checkbox").each(function (key,value) {
                        if (value.name === "checkBox"){
                            if (chooseAll.checked){
                                value.checked = true;
                            }else {
                                value.checked = false;
                            }
                        }
                    });
                }
                //删除选中商品
                $scope.deleteRows = function (dataItem) {
                    if(dataItem.sku === null || dataItem.sku === ""){
                        kendo.ui.ExtAlertDialog.showError("请先选择要包装的商品信息!");
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

                //辅料选择
                $scope.addFuRow = function (dataItem) {
                    if (dataItem.cargoOwnerId === undefined  ||dataItem.cargoOwnerId === ""  || dataItem.cargoOwnerId === 0){
                        kendo.ui.ExtAlertDialog.showError("请选择货主！");
                        return;
                    }

                    $scope.inventoryCargoOwnerPopup.initParam = function (subScope) {

                        subScope.param = dataItem.cargoOwnerId;
                    };
                    $scope.inventoryCargoOwnerPopup.refresh().open().center();
                    $scope.inventoryCargoOwnerPopup.setReturnData = function (resultData) {
                        if (_.isEmpty(resultData)){
                            return;
                        }
                        if(dataItem.skuId === resultData.skuId){
                            kendo.ui.ExtAlertDialog.showError("请选择与包装商品不同的辅料!");
                            return;
                        };
                        //辅料选择判断
                        var fuTable = document.getElementById("fuTable");
                        var fuArr = new Array();
                        for(var i = 1;i<fuTable.rows.length;i++){
                            fuArr.push(fuTable.rows[i].cells[9].innerHTML);
                        }
                        for (var i = 0 ;i<fuArr.length;i++){
                            if (fuArr[i] === resultData.skuId.toString()){
                                kendo.ui.ExtAlertDialog.showError("该辅料已选择,请重新选择辅料!");
                                return;
                            }
                        }

                        //辅料选择与商品选择判断
                        var proTable = document.getElementById("proTable");
                        var cargoOwnerInventoryIdArr = new Array();
                        for(var i = 1 ; i < proTable.rows.length ; i ++ ){
                            cargoOwnerInventoryIdArr.push(proTable.rows[i].cells[9].innerHTML);
                        }
                        for (var i = 0 ; i < cargoOwnerInventoryIdArr.length ; i ++){
                            if(cargoOwnerInventoryIdArr[i] === resultData.skuId.toString()){
                                kendo.ui.ExtAlertDialog.showError("该辅料已选择,请选择其它辅料!");
                                return;
                            }
                        }
                        var limit = "onchange='if(this.value != null){this.value=parseInt(this.value)<1?1:this.value}else{this.value=1}'";
                        var tr = "<tr>"+
                            "<td>"+"<input name='checkBox' type='checkbox'/>"+"</td>"+
                            "<td>"+resultData.skuName+"</td>" +
                            "<td>"+resultData.sku+"</td>"+
                            "<td>"+resultData.spec+"</td>"+
                            "<td>"+$filter('codeFormat')(resultData.unitType,'MasterUnit')+"</td>" +
                            "<td>"+$filter('storageRoomFormat')(resultData.storageRoomId)+"</td>"+
                            "<td>"+"<input type='number'"+limit+" value='1' required/>"+"</td>" +
                            "<td hidden='hidden'>"+resultData.id+"</td>" +
                            "<td hidden='hidden'>"+resultData.unitType+"</td>" +
                            "<td hidden='hidden'>"+resultData.skuId+"</td>" +
                            "<td hidden='hidden'>"+resultData.storageRoomId+"</td>" +
                            "</tr>";
                        $("#fuTable"+" tbody").append(tr);
                    };
                };

                //复选框全选
                $scope.fuChooseAll =function (dataItem) {
                    var fuChooseAll = document.getElementById("fuChooseAll");
                    $("#fuTable :checkbox").each(function (key,value) {
                        if (value.name === "checkBox"){
                            if (fuChooseAll.checked){
                                value.checked = true;
                            }else {
                                value.checked = false;
                            }
                        }
                    });
                }

                //删除选中商品
                $scope.deleteFuRows = function (dataItem) {
                    if(dataItem.sku === null || dataItem.sku === ""){
                        kendo.ui.ExtAlertDialog.showError("请先选择要包装的商品信息!");
                        return;
                    }
                    $("#fuTable :checkbox").each(function (key,value) {
                        if(value.name === "checkBox"){
                            if($(value).prop('checked')){
                                if(value.id !== "fuChooseAll"){
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