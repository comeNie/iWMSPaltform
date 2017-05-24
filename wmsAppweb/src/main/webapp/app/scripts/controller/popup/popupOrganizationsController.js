define(['scripts/controller/controller', '../../model/data/organizationsModel'], function(controller, organizationsModel) {
    "use strict";
    controller.controller('popupOrganizationsController', ['$scope','$rootScope','sync','url','wmsDataSource',
        function($scope, $rootScope, $sync, $url, wmsDataSource) {
            var organizationsUrl = $url.dataOrganizationsUrl,
                selectable = "row",
                organizationsPopupGridWidgetId = "userPopup",
                organizationsColumns = [
                    { title: '组织编号', field: 'id', align: 'left', width: "100px"},
                    { title: '组织名称', field: 'name', align: 'left', width: "100px"},
                    { title: '组织类型', field: 'typeCode', align: 'left', width: "100px" ,template:WMS.UTILS.codeFormat('typeCode','OrganizationType')},
                    { title: '联系人', field: 'contacts', align: 'left', width: "100px"},
                    { title: '联系电话', field: 'telephone', align: 'left', width: "100px"},
                    { title: '地址',field: 'address', align: 'left', width: "100px"},
                ];

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                widgetId: organizationsPopupGridWidgetId,
                dataSource: {},
                columns: organizationsColumns,
                editable: false,
                selectable: "row",
                height:350,
            }, $scope);

            $scope.$on("kendoWidgetCreated", function(event, widget){
                if (widget.options !== undefined && widget.options.widgetId === organizationsPopupGridWidgetId) {
                    if (_.isFunction($scope.organizationsPopup.initParam)) {
                        $scope.organizationsPopup.initParam($scope);
                        if ($scope.selectable) {
                            selectable = $scope.selectable;
                        }
                        if ($scope.url) {
                            organizationsUrl = $scope.url;
                            widget.setOptions({selectable: selectable,columns:organizationsColumns});
                        }
                        if ($scope.param) {
                            organizationsUrl = organizationsUrl + "/" + $scope.param;
                        }else{
                            organizationsUrl = organizationsUrl + "/" + 0;
                        }
                        var organizationsDataSource = wmsDataSource({
                            url: organizationsUrl,
                            schema: {
                                model: organizationsModel.organizationsHeader
                            }
                        });
                        widget.setDataSource(organizationsDataSource);
                        widget.refresh();
                    }
                }
            });

            $scope.selectOrganizations = function(){
                var selectView = $scope.organizationsPopupGrid.select();
                if (selectable !== "row") {
                    var selectedDataArray = _.map(selectView, function(view) {
                        return $scope.organizationsPopupGrid.dataItem(view);
                    });
                    if (_.isFunction($scope.organizationsPopup.setReturnData)) {
                        $scope.organizationsPopup.setReturnData(selectedDataArray);
                    }
                } else {
                    var selectedData = $scope.organizationsPopupGrid.dataItem(selectView);
                    if (_.isFunction($scope.organizationsPopup.setReturnData)) {
                        $scope.organizationsPopup.setReturnData(selectedData);
                    }
                }

                $scope.closeOrganizationsWindow();
            };

            $scope.closeOrganizationsWindow = function(){
                $scope.query = {};
                $scope.organizationsPopupGrid.dataSource.data([]);
                $scope.organizationsPopup.close();
            };

        }]);

});
