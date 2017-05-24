define(['scripts/controller/controller', '../../model/system/locationModel'], function (controller, location) {
    "use strict";
    controller.controller('locationController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {

            var locationHeaderUrl = $url.systemLocationUrl,
                locationHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: ' 仓库名称', field: 'warehouseId', align: 'left', width: "100px",template:WMS.UTILS.whFormat},
                    { title: ' 货区编号', field: 'zoneId', align: 'left', width: "100px",template:WMS.UTILS.zoneFormat("zoneId")},
                    { title: '货位编号', field: 'locationNo', align: 'left', width: "120px"},
                    { title: ' 启用', field: 'isActive',template:WMS.UTILS.checkboxDisabledTmp("isActive"), align: 'left', width: "100px"},
                    { title: '货架类型', field: 'typeCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('typeCode','LocationType')},
                    { title: '存货分类', field: 'classCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('classCode','LocationClass')},
                    { title: '操作方式', field: 'handlingCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('handlingCode','LocationHandling')},
                    { title: '货存放方式', field: 'categoryCode', align: 'left', width: "120px",template:WMS.UTILS.codeFormat('categoryCode','LocationCategory')},
                    { title: ' ABC分类', field: 'abcCode', align: 'left', width: "100px",template:WMS.UTILS.codeFormat('abcCode','AbcClass')},
                    { title: '允许多SKU混放', field: 'isMultisku',template:WMS.UTILS.checkboxDisabledTmp("isMultisku"), align: 'left', width: "120px"},
                    { title: '允许多批次混放', field: 'isMultilot',template:WMS.UTILS.checkboxDisabledTmp("isMultilot"), align: 'left', width: "120px"},
                    { title: '放货次序', field: 'putawaySeq', align: 'left', width: "120px"},
                    { title: '拣货次序', field: 'pickupSeq', align: 'left', width: "120px"},
                    { title: '容积', field: 'volume', align: 'left', width: "120px"},
                    { title: '长度', field: 'length', align: 'left', width: "120px"},
                    { title: '宽度', field: 'width', align: 'left', width: "120px"},
                    { title: '高度', field: 'height', align: 'left', width: "120px"},
                    { title: '承重', field: 'weightcapacity', align: 'left', width: "120px"},
                    { title: '货位描述', field: 'description', align: 'left', width: "120px"},
                    { title: '是否默认', field: 'isDefault',template:WMS.UTILS.checkboxDisabledTmp("isDefault"), align: 'left', width: "120px"},
                    { title: '是否可用', field: 'isUsed',template:WMS.UTILS.checkboxDisabledTmp("isUsed"), align: 'left', width: "120px"}
                ],
                locationHeaderDataSource = wmsDataSource({
                    url: locationHeaderUrl,
                    schema: {
                        model: location.locationtHeader
                    }
                });
            locationHeaderColumns = locationHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: locationHeaderDataSource,
                toolbar: [{ template:
                    '<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"}],
                columns: locationHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{
                        width:"680"
                    },
                    template: kendo.template($("#location-kendo-template").html())
                }
            }, $scope);
        }]);
})
