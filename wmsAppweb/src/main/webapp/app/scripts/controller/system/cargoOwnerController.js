define(['scripts/controller/controller', '../../model/system/cargoOwnerModel'], function (controller, cargoOwner) {
    "use strict";
    controller.controller('cargoOwnerController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {

            var cargoOwnerHeaderUrl = $url.systemCargoOwnerUrl,
                cargoOwnerHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '货主编号', field: 'cargoOwnerNo', align: 'left', width: "120px"},
                    { title: '简称', field: 'shortName', align: 'left', width: "120px"},
                    { title: '全称', field: 'fullName', align: 'left', width: "120px"},
                    { title: '货主类型', field: 'typeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('typeCode','StorerType')},
                    { title: '是否可用', align: 'left',field: 'isActive', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isActive")},
                    { title: '国家', field: 'country', align: 'left', width: "120px"},
                    { title: '省份', field: 'province', align: 'left', width: "120px"},
                    { title: '城市', field: 'city', align: 'left', width: "120px"},
                    { title: '区', field: 'area', align: 'left', width: "120px"},
                    { title: '邮政编码', field: 'zip', align: 'left', width: "120px"},
                    { title: '地址', field: 'address', align: 'left', width: "120px"},
                    { title: '联系人', field: 'contact', align: 'left', width: "120px"},
                    { title: '联系电话', field: 'telephone', align: 'left', width: "120px"},
                    { title: '传真', field: 'fax', align: 'left', width: "120px"},
                    { title: '邮件', field: 'email', align: 'left', width: "120px"},
                    { title: '描述', field: 'description', align: 'left', width: "120px"}

                ],
                cargoOwnerHeaderDataSource = wmsDataSource({
                    url:cargoOwnerHeaderUrl,
                    schema: {
                        model: cargoOwner.cargoOwnerHeader
                    }
                });
            cargoOwnerHeaderColumns = cargoOwnerHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: cargoOwnerHeaderDataSource,
                toolbar: [//{name: "create", text: "新增", className:'btn-auth-add'},
                    { template: '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"}
                    ],
                columns: cargoOwnerHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{
                        width:"620"
                    },
                    template: kendo.template($("#cargoOwner-kendo-template").html())
                }
            }, $scope);
        }]);
})
