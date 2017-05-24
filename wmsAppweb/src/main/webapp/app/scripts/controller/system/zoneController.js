define(['scripts/controller/controller', '../../model/system/zoneModel'], function (controller, zone) {
    "use strict";
    controller.controller('zoneController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var zoneUrl = $url.systemZoneUrl,
                zoneColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '货区编号', field: 'zoneNo', align: 'center', width: "120px"},
                    { title: '是否可用', field: 'isActive', align: 'center', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isActive")},
                    { title: '优先级', field: 'priority', align: 'center', width: "120px"},
                    { title: '是否可销售', field: 'isSaleAble', align: 'center', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isSaleAble")},
                    { title: '货区描述', field: 'description', align: 'center', width: "120px"}
                ],
                locationColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '货位编号', field: 'locationNo', align: 'center', width: "120px"},
                    { title: '货架类型', field: 'typeCode', align: 'center', width: "120px",template:WMS.UTILS.codeFormat('typeCode','LocationType')},
                    { title: '存货类型', field: 'classCode', align: 'center', width: "120px",template:WMS.UTILS.codeFormat('classCode','LocationClass')},
                    { title: '操作方式', field: 'handlingCode', align: 'center', width: "120px",template:WMS.UTILS.codeFormat('handlingCode','LocationHandling')},
                    { title: '存放方式', field: 'categoryCode', align: 'center', width: "120px",template:WMS.UTILS.codeFormat('categoryCode','LocationCategory')},
                    { title: 'ABC分类', field: 'abcCode', align: 'center', width: "100px",template:WMS.UTILS.codeFormat('abcCode','AbcClass')},
                    { title: '是否可用', field: 'isActive',template:WMS.UTILS.checkboxDisabledTmp("isActive"), align: 'center', width: "100px"},
                    { title: '货位描述', field: 'description', align: 'center', width: "120px"}
                ],

                zoneDataSource = wmsDataSource({
                    url: zoneUrl,
                    schema: {
                        model:zone.zoneHeader
                    }
                });

                zoneColumns = zoneColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
                locationColumns=locationColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

                $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                    dataSource: zoneDataSource,
                    toolbar: [{ name: "create", text: "新增", className:'btn-auth-add'}],
                    columns: zoneColumns,
                    editable: {
                        mode: "popup",
                        window:{width:"300"},
                        template: kendo.template($("#zone-kendo-template").html())
                    }
                }, $scope);


                $scope.zoneLocationOptions = function (dataItem) {

                    var defaultOptions = {
                        dataSource: wmsDataSource({
                            url: zoneUrl + "/" + dataItem.id+ "/location",
                            schema: {
                                model: zone.locationHeader
                            }
                        }),
                        columns: locationColumns,
                        editable: {
                            mode: "popup",
                            window: {
                                width: "640px"
                            },
                            template: kendo.template($("#zone-location-kendo-template").html())
                        },
                       toolbar: [{ name: "create", text: "新增", className:'btn-auth-add'}],
                    };

                    return WMS.GRIDUTILS.getGridOptions(defaultOptions, $scope);
                };

        }]);
})