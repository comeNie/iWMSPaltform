define(['scripts/controller/controller', '../../model/system/skuCategorysModel'], function (controller, skuCategorys) {
    "use strict";
    controller.controller('skuCategorysController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {

            var skuCategorysHeaderUrl = $url.systemSkuCategorysUrl,
                skuCategorysHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '类别编码', field: 'categoryCode', align: 'left', width: "120px"},
                    { title: '类别名称', field: 'categoryName', align: 'left', width: "120px"},
                    { title: '排列顺序', field: 'position', align: 'left', width: "120px"},
                    { title: '描述', field: 'description', align: 'left', width: "120px"}
                ],
                skuCategorysHeaderDataSource = wmsDataSource({
                    url: skuCategorysHeaderUrl,
                    schema: {
                        model: skuCategorys.skuCategoryHeader
                    }
                });
            skuCategorysHeaderColumns  = skuCategorysHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: skuCategorysHeaderDataSource,
                toolbar: [{ template:
                    '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"}],
                columns: skuCategorysHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{
                        width:"350"
                    },
                    template: kendo.template($("#skuCategorys-kendo-template").html())
                }
            }, $scope);


            $scope.detailGridOptions = function(dataItem){
                var skuCategorysUrl = skuCategorysHeaderUrl + "/" + dataItem.id;
                return WMS.GRIDUTILS.getGridOptions({
                    dataSource:wmsDataSource({
                        url: skuCategorysUrl,
                        schema:{
                            model: skuCategorys.skuCategoryHeader
                        }
                    }),
                    pageable:false,
                    columns:skuCategorysHeaderColumns,
                    editable: {
                        mode: "popup",
                        window:{
                            width:"350"
                        },
                        template: kendo.template($("#skuCategorys-kendo-template").html())
                    }
                }, $scope);
            };

        }]);
})