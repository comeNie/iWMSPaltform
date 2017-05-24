define(['scripts/controller/controller'], function (controller) {
    "use strict";
    controller.controller('shipmentDeliveryController',['$scope', '$rootScope','$filter', 'sync', 'url', 'wmsDataSource','wmsReportPrint',
        function($scope, $rootScope, $filter, $sync, $url, wmsDataSource,wmsReportPrint) {

            var deliveryUrl = $url.outwhShipmentHeaderUrl,
                shipmentDeliveryColumns = [
                    { title: '交接单号（车号）', field: 'vehicleNo', align: 'left', width: "200px"},
                    { title: '承运商', field: 'expressName', align: 'left', width: "200px"},
                    { title: '物流(包裹)单号', field: 'expressNo', align: 'left', width: "200px"},
                    { title: '出库单号', field: 'id', align: 'left', width: "200px"},
                    { title: '交接时间', field: 'updateTime', align: 'left', width: "150px",template:WMS.UTILS.timestampFormat("updateTime","yyyy-MM-dd HH:mm:ss")}
                ]
                ;
            //初始化dataSource为空
            var shipmentDeliveryDataSource = new kendo.data.DataSource({
                data: []
            });

            $scope.mainGridOptions = {
                dataSource: shipmentDeliveryDataSource,
                toolbar: [
                    {className:"btn-auth-print",
                        template: //'<kendo-button  class="k-primary" ng-click="print()">打印</kendo-button>' +
                        // '<lable style="margin-left: 10px;">打印份数:</lable>' +
                        // '<input type="number" style="width: 30px;height: 20px;border-bottom-width: 2px;padding-bottom: 3px;border-right-width: 2px;margin-left: 5px;" name="printCount" id="printCount" value="3" />'+
                        '<lable style="margin-left: 10px;">包裹单数:&nbsp;</lable><strong id="shipmentTotal" style="margin-left: 20px;font-size: 25px;">0</strong>'
                    }
                ],
                columns: shipmentDeliveryColumns,
                autoBind: true,
                editable: false
            };

            //运单号的回车事件
            $("#shipmentId").on("keydown", function (ev) {
                if (ev.keyCode === 13) {
                    var expressName = $.trim($("#expressName").val());
                    if (expressName === '') {
                        kendo.ui.ExtAlertDialog.showError("请选择承运商!");
                        $("#shipmentId").val("");
                        return;
                    }
                    var shipmentId = $.trim($("#shipmentId").val());
                    if (shipmentId === '') {
                        kendo.ui.ExtAlertDialog.showError("请输入出库单号!");
                        return;
                    }
                    var vehicleNo = $.trim($("#vehicleNo").val());
                    if (vehicleNo === '') {
                        kendo.ui.ExtAlertDialog.showError("请输入车号!");
                        return;
                    }
                    $sync(deliveryUrl+"/delivery", "PUT",{data: { shipmentId: $("#shipmentId").val(), vehicleNo: $("#vehicleNo").val(), expressName: $("#expressName").val()}})
                        .then(function (data) {
                            prependRemind("<span style='background-color:#76c3f9;'>" + data.message + "</span>");
                            $("#shipmentId").val("");
                            $("#shipmentId").focus();
                            $(".k-primary").attr("disabled",false);
                            $("#expressName").attr("disabled",true);
                            $("#expressName").css("background-color", "#E4DFDF");
                            //获取数据填充DataSource
                            var datas = shipmentDeliveryDataSource.data();
                            if (datas) {
                                $.each(data.result.rows,function(){
                                    datas.push(this);
                                });
                            }else{
                                datas = data.result.rows;
                            }
                            shipmentDeliveryDataSource.data(datas);
                            $("#shipmentTotal").html(shipmentDeliveryDataSource.data().length);

                        },function(data){
                            prependRemind("<span style='background-color:#FD6552;'>" + data.message + "</span>");
                            $("#shipmentId").val("");
                            $("#shipmentId").focus();
                        });
                }
            });

            $("#expressName").change(function(){
                var expressNameVal = $("#expressName").val();
                if(expressNameVal!=""){
                    $("#shipmentId").val("");
                    $("#shipmentId").focus();
                }
            });

            //添加提示信息
            function prependRemind(message) {
                $("#logsDiv").prepend(message + "<br/>");
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
                    //$scope.dnHeader.set("carrierId",resultData.id);
                    //$scope.dnHeader.set("expressName",resultData.name);
                    $("#expressName").val(resultData.name);
                }
            }



         }
    ])
})