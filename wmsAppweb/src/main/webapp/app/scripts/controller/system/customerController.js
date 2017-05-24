define(['scripts/controller/controller', '../../model/system/customerModel'], function (controller, customer) {
    "use strict";
    controller.controller('customerController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {

            var customerHeaderUrl = $url.systemCustomerUrl,
                customerHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    //{ title: '客户编号', field: 'id', align: 'left', width: "100px"},
                    { title: '客户名称', field: 'customerName', align: 'left', width: "100px"},
                    // { title: '所属组织', field: 'organizationId', align: 'left', width: "100px",template: WMS.UTILS.organizationsFormat("organizationId")},
                    { title: '客户类型', field: 'typeCode', align: 'left', width: "100px" ,template:WMS.UTILS.codeFormat('typeCode','CustomerType')},
                    { title: '是否激活', field: 'isActive', align: 'left', width: "100px", template: WMS.UTILS.checkboxDisabledTmp("isActive")},
                    { title: '客户电话', field: 'telephone', align: 'left', width: "100px"},
                    { title: '客户联系人', field: 'contacts', align: 'left', width: "100px"},
                    { title: '客户地址', field: 'address', align: 'left', width: "100px"},
                    { title: '客户备注', field: 'description', align: 'left', width: "100px"},
                ],
                customerHeaderDataSource = wmsDataSource({
                    url: customerHeaderUrl,
                    schema: {
                        model: customer.customerHeader
                    }
                });
            customerHeaderColumns = customerHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: customerHeaderDataSource,
                toolbar: [{ template:
                    '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"}],
                columns: customerHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{
                        width:"480"
                    },
                    template: kendo.template($("#customer-kendo-template").html())
                }
            }, $scope);
        }]);
})
