define(['scripts/controller/controller'], function (controller) {
    "use strict";
    controller.controller('processController', ['$scope', '$rootScope', '$filter', 'sync', 'url','wmsDataSource',
        function ($scope, $rootScope, $filter, $sync, $url, wmsDataSource) {
            var processUrl = $url.outwhProducerProcessUrl+'/process';
            //数据集
            var processDataSource = wmsDataSource({
                url: processUrl,
                schema: {
                    model: {
                        id: "id",
                        fields: {
                            id: { editable: false, nullable: true },
                            inventoryId: "inventoryId",
                            skuName: "skuName",
                            skuType: "skuType",
                            spec: "spec",
                            qty: { type: "number" }
                        }
                    }
                }
            });

            $("#pager").kendoPager({
                dataSource: processDataSource
            });
             //绑定listView
            var listView = $("#listView").kendoListView({
                dataSource: processDataSource,
                //navigatable: true,
                template: kendo.template($("#template").html())
            }).data("kendoListView");

            //添加
            $(".k-add-button").click(function(e) {
                listView.add();
                e.preventDefault();
            });

            //条件查询
            $scope.searchGrid = function () {
                var skuName = $("#skuName").val();
                var sku = $("#sku").val();
                var cargoOwnerBarcode = $("#cargoOwnerBarcode").val();
                var storageRoomId = $("#storageRoomId").val();
                var params = {
                    skuName:skuName,
                    sku:sku,
                    cargoOwnerBarcode:cargoOwnerBarcode,
                    storageRoomId:storageRoomId
                };
                $sync( processUrl ,"GET",{data:params}).then(function (xhr) {
                    $("#listView").kendoListView({
                        dataSource: xhr.result.rows,
                        template: kendo.template($("#template").html()),
                    }).data("kendoListView");
                })
            }
        }
    ])
});
