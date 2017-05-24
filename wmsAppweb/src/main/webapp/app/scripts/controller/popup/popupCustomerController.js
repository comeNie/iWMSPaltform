define(['scripts/controller/controller', '../../model/system/customerModel'], function (controller, customerModel) {
    "use strict";
    controller.controller('popupCustomerController', ['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource',
        function ($scope, $rootScope, $sync, $url, wmsDataSource) {
            var customerUrl = $url.dataCustomerUrl,
                selectable = "row",
                customerColumns = [
                    {title: '客户编号', field: 'id', align: 'left', width: "100px"},
                    {title: '客户名称', field: 'customerName', align: 'left', width: "100px"},
                    {
                        title: '客户类型',
                        field: 'typeCode',
                        align: 'left',
                        width: "100px",
                        template: WMS.UTILS.codeFormat('typeCode', 'CustomerType')
                    },
                    {title: '联系人', field: 'contacts', align: 'left', width: "100px"},
                    {title: '联系电话', field: 'telephone', align: 'left', width: "100px"},
                    {title: '地址', field: 'address', align: 'left', width: "100px"},
                ],
                customerDataSource =  wmsDataSource({
                    url: customerUrl,
                    schema: {
                        model: customerModel.customerHeader
                    }
                });

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: customerDataSource,
                columns: customerColumns,
                editable: false,
                selectable: "row",
                height: 350,
            }, $scope);

            $scope.selectCustomer = function () {
                var selectView = $scope.customerGrid.select();
                if (selectable !== "row") {
                    var selectedDataArray = _.map(selectView, function (view) {
                        return $scope.customerGrid.dataItem(view);
                    });
                    if (_.isFunction($scope.customerPopup.setReturnData)) {
                        $scope.customerPopup.setReturnData(selectedDataArray);
                    }
                } else {
                    var selectedData = $scope.customerGrid.dataItem(selectView);
                    if (_.isFunction($scope.customerPopup.setReturnData)) {
                        $scope.customerPopup.setReturnData(selectedData);
                    }
                }

                $scope.closeCustomerWindow();
            };

            $scope.closeCustomerWindow = function () {
                $scope.query = {};
                $scope.customerGrid.dataSource.data([]);
                $scope.customerPopup.close();
            };

        }]);

});
