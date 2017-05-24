
define(['scripts/controller/controller', '../../model/data/skuModel'], function (controller, sku) {
    "use strict";
    controller.controller('skuController',['$scope', '$rootScope', 'sync', 'url','FileUploader', 'wmsDataSource','wmsReportPrint',
        function($scope, $rootScope, $sync, $url, FileUploader, wmsDataSource,wmsReportPrint) {

            var skuHeaderUrl = $url.systemSkuUrl,
                skuHeaderColumns = [
                    WMS.GRIDUTILS.CommonOptionButton(),
                    { title: '商品名称', field: 'itemName', align: 'left', width: "260px"},
                    { title: '数据来源', align: 'left',field: 'datasourceCode', width: "120px",template:WMS.UTILS.codeFormat('datasourceCode','DataSource')},
                    { title: '商品编码', align: 'left',field: 'sku', width: "150px"},
                    { title: '商品条码', align: 'left',field: 'barcode', width: "150px"},
                    { title: '商品类别', align: 'left',field: 'categorysId', width: "120px",template:WMS.UTILS.skuCategorysFormat("categorysId")},
                    { title: '商品规格', align: 'left',field: 'spec', width: "200px"},
                    { title: '单位类型', align: 'left',field: 'unitType', width: "120px",template:WMS.UTILS.codeFormat('unitType','MasterUnit')},
                    { title: '来源地', align: 'left',field: 'originPlace', width: "120px"},
                    { title: '净重', align: 'left',field: 'netWeight', width: "120px"},
                    { title: '毛重', align: 'left',field: 'grossWeight', width: "120px"},
                    { title: 'UPC编码', align: 'left',field: 'upc', width: "120px"},
                    { title: '仓库温度', align: 'left',field: 'warehouseTemp', width: "120px"},
                    { title: '运输温度', align: 'left',field: 'transportTemp', width: "120px"},
                    { title: '是否可用', align: 'left',field: 'isActive', width: "120px",template:WMS.UTILS.checkboxDisabledTmp("isActive")},
                    { title: '实际价格', align: 'left',field: 'costPrice', width: "120px"},
                    { title: '生产者', align: 'left',field: 'producer', width: "120px"},
                    { title: '生产类别', align: 'left',field: 'producerCode', width: "120px"},
                    { title: '销货价1', align: 'left',field: 'price1', width: "120px"},
                    { title: '销货价2', align: 'left',field: 'price2', width: "120px"},
                    { title: '销货价3', align: 'left',field: 'price3', width: "120px"},
                    { title: '描述', align: 'left',field: 'description', width: "120px"},


                ],
                skuHeaderDataSource = wmsDataSource({
                    url:skuHeaderUrl,
                    schema: {
                        model: sku.skuHeader
                    }
                });
            skuHeaderColumns = skuHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);
            skuHeaderColumns.splice(0, 0, WMS.UTILS.CommonColumns.checkboxColumn);
            $scope.selectSingleRow = WMS.GRIDUTILS.selectSingleRow;
            $scope.selectAllRow = WMS.GRIDUTILS.selectAllRow;

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: skuHeaderDataSource,
                toolbar: [
                    { template:'<a class="k-button k-button-icontext k-grid-add" href="\\#"><span class="k-icon k-add"></span>新增</a>',className:"btn-auth-add"},
                    { template:'<a class="k-button k-grid-custom-command" href="\\#" ng-click="batchDelete()" >批量删除</a>', className:"btn-auth-batchDelete"},
                    { template:'<a class="k-button k-button-icontext k-grid-edit" href="\\#" ng-click="print()">打印条码</a>',className:"btn-auth-print"},
                    { template:'<a class="k-button k-grid-custom-command" href="\\#" ng-click="import($event,skuHeaderGrid)">导入</a>',className:"btn-auth-import"}
                ],
                columns: skuHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{
                        width:"620"
                    },
                    template: kendo.template($("#sku-kendo-template").html())
                }
            }, $scope);
            //打印商品条码
            $scope.print = function () {
                var skuHeaderGrid = this.skuHeaderGrid;
                var selectView = WMS.GRIDUTILS.getCustomSelectedData(skuHeaderGrid);
                var selectedDataArray = _.map(selectView, function(view) {
                    return view.id;
                });
                var selectDataIds = selectedDataArray.join(",");
                if(selectDataIds === ""){
                    kendo.ui.ExtAlertDialog.showError("请选择要打印的数据!");
                    return;
                }
                wmsReportPrint.printSkuBarcodeByIds(selectDataIds,1);
            }

            //批量删除
            $scope.batchDelete = function (e) {
                var ids = "";
                var skuHeaderGrid = this.skuHeaderGrid;
                var selectedData = WMS.GRIDUTILS.getCustomSelectedData(skuHeaderGrid);
                for(var i=0;i<selectedData.length;i++){
                    ids+=selectedData[i].id+",";
                }
                ids = ids.substring(0,ids.length-1);
                if(ids ===""){
                    kendo.ui.ExtAlertDialog.showError("请至少选择一条数据.");
                    return;
                }
                $sync(skuHeaderUrl +"/batch/" + ids, "DELETE")
                    .then(function (xhr) {
                        skuHeaderGrid.dataSource.read({});
                    }, function (xhr) {
                        skuHeaderGrid.dataSource.read({});
                    });
            }
        }
    ]);
})
