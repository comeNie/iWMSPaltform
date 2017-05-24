
define(['scripts/controller/controller', '../../model/log/operateLogModel'],function (controller,operateLog) {
    "user strict";
    controller.controller('reportOperateLogController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function ($scope, $rootScope, $sync, $url, wmsDataSource) {
            var operationLogUrl = $url.reportOperateLogUrl,
                operationLogColumns=[
                        { title: '货主', field: 'cargoOwnerId', align: 'left', width: "120px",template:WMS.UTILS.cargoOwnerFormat("cargoOwnerId")},
                        { title: '单据类型', field: 'orderTypeCode', align: 'left', width: "100px", template: WMS.UTILS.codeFormat("orderTypeCode", "OrderType")},
                        { title: '单据编号', field: 'orderNo', align: 'left', width: "100px"},
                        { title: '操作类型', field: 'operationCode', align: 'left', width: "100px", template: WMS.UTILS.codeFormat("operationCode", "OrderOperations")	},
                        { title: '描述', field: 'description', align: 'left', width: "160px"},
                     { title: '操作人', field: 'createUser', align: 'left', width: "120px"},
                    { title: '操作时间', field: 'createTime', align: 'left', width: "140px", template: WMS.UTILS.timestampFormat("createTime")},
                ],
                operationLogDataSource = wmsDataSource({
                    url: operationLogUrl,
                    schema: {
                        model: operateLog.header
                    }
                });
            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource:operationLogDataSource,
                exportable: true,
                columns: operationLogColumns,
                editable:false
            },$scope)





        }

    ]);

})
