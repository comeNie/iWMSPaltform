define(['scripts/controller/controller', '../../model/system/tenantModel'], function (controller, tenant) {
    "use strict";
    controller.controller('tenantController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
            function($scope, $rootScope, $sync, $url, wmsDataSource) {

                var tenantHeaderUrl = $url.systemTenantUrl,
                    tenantHeaderColumns = [
                        WMS.GRIDUTILS.CommonOptionButton(),
                        { title: '租户Id', field: 'id', align: 'left', width: "100px"},
                        { title: '租户编号', field: 'tenantNo', align: 'left', width: "120px"},
                        { title: '租户名称', field: 'description', align: 'left', width: "150px"},
                        { title: '租户类型', field: 'typeCode', align: 'left', width: "120px"}
                    ],
                    tenantHeaderDataSource = wmsDataSource({
                        url: tenantHeaderUrl,
                        schema: {
                            model: tenant.tenantHeader
                        }
                    });
                tenantHeaderColumns = tenantHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
                $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                    dataSource: tenantHeaderDataSource,
                    toolbar: [{ template:
                        '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"}],
                    columns: tenantHeaderColumns,
                    editable: {
                        mode: "popup",
                        window:{
                            width:"380"
                        },
                        template: kendo.template($("#tenant-kendo-template").html())
                    }
                }, $scope);
            }]);
})