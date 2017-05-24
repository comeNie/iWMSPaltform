define(['app'], function (app) {
    'use strict';
    app.factory('wmsReportPrint', ['sync', 'url', 'wmsPrint', function ($sync, url, wmsPrint) {
            var wmsReportPrint = function () {
            };
            function checkPrintData(data) {
                if (data.length == 0) {
                    kendo.ui.ExtAlertDialog.showError("没有要打印的数据!");
                    return false;
                }
                return true;
            }

            //获取当前环境所配置的打印机
            wmsReportPrint.prototype.getCurrentMachines = function () {
                return wmsPrint.getCurrentMachines();
            };

            //获取默认打印机
            wmsReportPrint.prototype.getDefaultMachine = function () {
                return wmsPrint.getDefaultMachine();
            };


           /////////////////////////////////对外暴露方法开始/////////////////////////////////////
            /**
             * 打印商品条码
             * @param ids
             * @param printCount 打印份数
             */
            wmsReportPrint.prototype.printSkuBarcodeByIds = function (ids, printCount) {
                var url = "/sku/print/" + ids;
                $sync(window.BASEPATH + url, "GET")
                    .then(function (xhr) {
                        if (checkPrintData(xhr)) {
                            var resultData = xhr.result.rows;
                            var result = [];
                            if (printCount == 1) {
                                wmsPrint.printSkuBarcode(resultData);
                            } else {
                                $.each(resultData, function () {
                                    for (var i = 0; i < printCount; i++) {
                                        result.push(this);
                                    }
                                });
                                wmsPrint.printSkuBarcode(result);
                            }
                        }
                    }, function (xhr) {
                        kendo.ui.ExtAlertDialog.showError(xhr);
                    });
            }



            /**
             * 打印入库单
             * @param ids
             */
            wmsReportPrint.prototype.printReceipts = function (ids,printCount,printType) {
                var url = "/receipt/print/"+ids;
                $sync(window.BASEPATH + url, "GET")
                    .then(function (xhr) {
                        if (checkPrintData(xhr)) {
                            wmsPrint.printReceipt(xhr.result.rows,printCount,printType);
                        }
                    }, function (xhr) {
                        kendo.ui.ExtAlertDialog.showError(xhr);
                    });
            };

        /**
         * 打印出库单
         * @param ids
         */
        wmsReportPrint.prototype.printShipment = function (ids,printCount) {
            var url = "/shipment/printShipment/"+ids;
            $sync(window.BASEPATH + url, "GET")
                .then(function (xhr) {
                    if (checkPrintData(xhr)) {
                        wmsPrint.printShipment(xhr.result.rows,printCount);
                    }
                }, function (xhr) {
                    kendo.ui.ExtAlertDialog.showError(xhr);
                });
        };

        /**
         * 打印拣货单
         * @param ids
         */
        wmsReportPrint.prototype.printPicking = function (ids) {
            var url = "/shipment/printPicking/"+ids;
            $sync(window.BASEPATH + url, "GET")
                .then(function (xhr) {
                    if (checkPrintData(xhr)) {
                        wmsPrint.printPicking(xhr.result.rows);
                    }
                }, function (xhr) {
                    kendo.ui.ExtAlertDialog.showError(xhr);
                });
        };


        /////////////////////////////////对外暴露方法接口/////////////////////////////////////

         return new wmsReportPrint();
    }]);
});

