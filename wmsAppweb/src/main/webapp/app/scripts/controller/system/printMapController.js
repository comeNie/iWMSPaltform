define(['scripts/controller/controller', '../../model/system/printMapModel'], function (controller, printMap) {
    "use strict";
    controller.controller('printMapController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {

            var printMapHeaderUrl = $url.systemPrintMapUrl,
                printMapHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '打印代码', field: 'code', align: 'left', width: "120px"},
                    { title: '打印名称', field: 'name', align: 'left', width: "120px"},
                    { title: '详细字段', field: 'detail', align: 'left', width: "120px",template:function(dataItem) { return WMS.UTILS.tooLongContentFormat(dataItem,"detail") }},
                    { title: '是否详细', align: 'left',field: 'hasDetail', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("hasDetail")},

                ],
                printMapHeaderDataSource = wmsDataSource({
                    url:printMapHeaderUrl,
                    schema: {
                        model: printMap.printMapHeader
                    }
                });
            printMapHeaderColumns = printMapHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: printMapHeaderDataSource,
                toolbar: [{ template:
                    '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"}],
                columns: printMapHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{
                        width:"680"

                    },
                    template: kendo.template($("#printMap-kendo-template").html())
                }
            }, $scope);
        }]);
})
