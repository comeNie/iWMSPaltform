define(['scripts/controller/controller', '../../model/system/organizationsModel'], function (controller, organizations) {
    "use strict";
    controller.controller('organizationsController', ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function ($scope, $rootScope, $sync, $url, wmsDataSource) {

            var organizationsHeaderUrl = $url.systemOrganizationsUrl,
                organizationsHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    {title: '机构名称', field: 'name', align: 'left', width: "120px"},
                    {title: '机构类型', field: 'typeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('typeCode','OrganizationType')},
                    {title: '上级机构', field: 'parentId', align: 'left', width: "120px",template: WMS.UTILS.organizationsFormat("parentId")},
                    {title: '是否启用', align: 'left', field: 'isActive', width: "120px", template: WMS.UTILS.checkboxDisabledTmp("isActive")},
                    {title: '联系人', align: 'left', field: 'contacts', width: "120px"},
                    {title: '地址', align: 'left', field: 'address', width: "120px"},
                    {title: '联系电话', align: 'left', field: 'telephone', width: "120px"},

                ],
                organizationsHeaderDataSource = wmsDataSource({
                    url: organizationsHeaderUrl,
                    schema: {
                        model: organizations.organizationsHeader
                    }
                });
            organizationsHeaderColumns = organizationsHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: organizationsHeaderDataSource,
                toolbar: [{
                    template: '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',
                    className: "btn-auth-add"
                }],
                columns: organizationsHeaderColumns,
                editable: {
                    mode: "popup",
                    window: {
                        width: "400"
                    },
                    template: kendo.template($("#organizations-kendo-template").html())
                }
            }, $scope);
        }]);
})
