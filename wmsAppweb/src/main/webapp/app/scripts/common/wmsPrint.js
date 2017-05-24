define(['app'], function (app) {
    app.factory('wmsPrint', ['sync', 'url', function ($sync, url) {

        var Print_Detail = "PRINT_DETAIL_DIV_";
        var Print_Div = "PRINT_DIV_";

        var WmsPrint = function () {
        }//打印对象
        var LODOP;
        //打印模板对象
        var WmsPrintTemplate = [];
        //打印映射
        var PRINT_CONTENT = {};
        //加载打印模板
        $sync(window.BASEPATH + "/print/temp/list", "get").then(function (data) {
           WmsPrintTemplate = data.result;
        });

        //加载模板参数
        $sync(window.BASEPATH + "/print/map/list", "get").then(function (data) {
           $(data.result).each(function () {
               PRINT_CONTENT[this.code] = this;
           });
        });

        /**
         * 获得LODOP对象
         * @returns {*}
         */
        function _lodopCustom() {
            return getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
        }
        //打印机设置
        function _machine() {
            LODOP = _lodopCustom();
            var iPrinterCount = LODOP.GET_PRINTER_COUNT();//获取打印机设备数量
            var printerDevice = [];//设备集合
            for (var i = 0; i < iPrinterCount; i++) {
                var printerName = LODOP.GET_PRINTER_NAME(i);
                var printer = {key: printerName, value: printerName};
                printerDevice.push(printer);
            }
            return printerDevice;
        }
        //默认打印机设置
        function _defaultMachine() {
            return LODOP.GET_PRINTER_NAME(-1);
        }

        /**
         * 设置纸张信息
         * @param ori
         * @param width
         * @param height
         * @param pageType
         */
        function setPageSize(ori, width, height, pageType) {
            if (ori == '' || ori === undefined) {
                orig = '0';
            }
            if (width == '' || width === undefined)width = 0;
            if (height == '' || height === undefined)height = 0;
            if (pageType == undefined) {
                pageType = "";
            }
            LODOP.SET_PRINT_PAGESIZE(ori, width, height, pageType);
        }

        //错误消息提示
        function error(message) {
            return {suc: false, message: message};
        }
        //成功消息
        function success() {
            return {suc: true};
        }

        function _content(key) {
            return PRINT_CONTENT[key];
        }


        /**
         * 执行打印入口
         * @param printData     需要打印的数据
         * @param type          打印类别
         * @param templateObj   打印模版数据
         * @param copies   打印份数
         * @returns {boolean}
         */
        function printExec(printData, type, templateObj, copies, printer) {
            if (templateObj == null) {
                kendo.ui.ExtAlertDialog.showError(type + "没有设置模板!");
                return error("没有设置模板信息!");
            }
            LODOP = _lodopCustom();
            LODOP.PRINT_INIT(type + "打印");
            if (copies) {
                LODOP.SET_PRINT_COPIES(copies);
            } else {
                LODOP.SET_PRINT_COPIES(1);
            }

            //打印参数
            var printOptions = templateObj.printOptions;
            if (typeof printOptions == "string") {
                printOptions = $.parseJSON(printOptions)
            }
            if (printer) {
                printOptions.printMachine = printer;
            }

            if (printOptions !== undefined && (printOptions.pageLength !== undefined || printOptions.pageWidth !== undefined)) {
                //设置纸张大小
                setPageSize(printOptions.pageOri, printOptions.pageWidth, printOptions.pageLength, "");
            }
            //如果设置了打印机 则选择设置的打印机
            if (printOptions.printMachine !== undefined && printOptions.printMachine != '') {
                LODOP.SET_PRINTER_INDEX(printOptions.printMachine);
            } else if (printOptions.printMachine == undefined || printOptions.printMachine == '') {
                LODOP.SET_PRINTER_INDEX(-1);//取默认打印机
            } else {
                return error("没有获取到打印机,请检查设备是否正常.");
            }
            var keyMap = _content(type);
            //是否多条打印
            var isArray = $.isArray(printData);
            if (isArray) {
                if (printData.length == 0) {
                    return error("不存在可打印的数据!")
                }
                for (var i = 0; i < printData.length; i++) {
                    printDataFunc(printData[i], type, keyMap, templateObj, i, type);
                }
            } else {
                printDataFunc(printData, type, keyMap, templateObj, 0, type);
            }
            //预览
            //LODOP.PREVIEW();
            var printFlag = LODOP.PRINT();
            return {suc: printFlag};
        }

        function printDataFunc(o, type, keyMap, templateObj, dataIndex) {
            var field = keyMap.field;
            var contentObj = templateObj.content;
            if (typeof contentObj == "string") {
                contentObj = $.parseJSON(contentObj)
            }
            var itemArr = contentObj.item;
            var tableIndex = 0;
            for (var i = 0; i < itemArr.length; i++) {
                var item = itemArr[i];
                if (item.styleA !== undefined && item.styleA.PreviewOnly == true) {
                    continue;
                }
                var className = item.className;
                var styleA = item.styleA;
                if (className == 'Text' || className == 'BarCode' || className == 'Rect' || className == 'Image') {
                    var contentCn = item.ItemContent;
                    var repalceObj = _fieldEnByCn(contentCn, field)
                    var replacesStr = ''
                    if (repalceObj !== undefined) {
                        if (o[repalceObj.en] !== undefined) {
                            replacesStr = o[repalceObj.en];
                        }
                    }
                    var result = replaceStr(contentCn, repalceObj.cn, replacesStr);
                    if (className == 'BarCode') {
                        LODOP.ADD_PRINT_BARCODE(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, item.styleA.FontName, result);
                        if (!styleA.showBarCode) {//条形码显示文字开关
                            LODOP.SET_PRINT_STYLEA(0, "ShowBarText", 0);
                        }
                    } else if (className == 'Rect') {
                        LODOP.ADD_PRINT_RECT(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, 0, 1);
                    }
                    else if (className == 'Image') {
                        if (result) {
                            LODOP.ADD_PRINT_IMAGE(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, result);
                        } else {
                            LODOP.ADD_PRINT_HTM(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, "谢谢惠顾!");
                        }
                    }
                    else {
                        LODOP.ADD_PRINT_TEXT(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, result);
                    }
                    for (var id in styleA) {
                        if (styleA[id] != '' && styleA[id] != 0 && styleA[id] != '#000000') {
                            if (id == 'LinkedItem') {
                                LODOP.SET_PRINT_STYLEA(0, "LinkedItem", styleA[id] * (dataIndex + 1));
                            } else {
                                LODOP.SET_PRINT_STYLEA(0, id, styleA[id]);
                            }
                        }
                    }
                } else {
                    if (dataIndex == 0) {
                        if (keyMap.hasDetail == 1) {
                            if ($("#" + Print_Detail).length <= 0) {
                                $("body").append("<div id='" + Print_Detail + "' class='display:none;'></div>");
                            } else {
                                $("#" + Print_Detail).html("");
                            }
                            $("#" + Print_Detail).html(item.ItemContent);

                        }
                    }
                    tableIndex = item.index;
                    _detailContent(keyMap, type, o, item)
                }
            }


            //做关联处理
            if (tableIndex != 0) {
                var itemCount = itemArr.length;
                for (var i = 0; i < itemArr.length; i++) {
                    var item = itemArr[i];
                    if (item.styleA.ItemType == 1) {
                        var sourctIndex = (dataIndex * itemCount) + parseInt(item.index);
                        var targetIndex = (dataIndex * itemCount) + parseInt(tableIndex);
                        LODOP.SET_PRINT_STYLEA(sourctIndex, "LinkedItem", targetIndex);
                    }
                }
            }
            //分页
            LODOP.NewPageA();
        }

        function _fieldEnByCn(cn, field) {
            if (typeof field == "string") {
                field = $.parseJSON(field);
            }
            var obj = {};
            for (var i = 0; i < field.length; i++) {
                if (cn.indexOf(field[i].cn) != -1) {
                    obj = field[i];
                    break;
                }
            }

            return obj;
        }

        function replaceStr(content, dest, ori) {
            if (ori == undefined) {
                ori = '';
            }

            var contentNew = content.replace(dest, ori);

            return contentNew;
        }

        /**
         * 获得打印明细内容
         * @param line
         * @param keyMap
         * @param o
         * @param tableObj
         */
        function _detailContent(keyMap, type, o, tableObj) {
            var htmlStr = '';
            var tmpHtml;
            if (keyMap.hasDetail != 1) {
                $("#" + Print_Div).append(tableObj.ItemContent);
            } else {
                tmpHtml = $("#" + Print_Detail).html();
                $("#" + Print_Div).append(tmpHtml);
                var tr = $("#" + Print_Div + " table tbody tr").eq(0).clone();
                var trHtml = tr.html();
                var detailDataArr = o.list
                if(o.list !== null){
                    var detailKeyMap = $.parseJSON(keyMap.detail);
                    for (var i = 0; i < detailDataArr.length; i++) {
                        trHtml = tr.html();
                        var detailData = detailDataArr[i];
                        for (var detailKey in detailKeyMap) {
                            var oriStr = ''
                            if (detailData[detailKey] != undefined && detailData[detailKey] != 'undefined') {
                                oriStr = detailData[detailKey];
                            }
                            trHtml = replaceStr(trHtml, detailKeyMap[detailKey], oriStr)
                        }
                        var tfootHtml;
                        if(type === "Receipt" || type === "StoreReceipt" || type === "Shipment" || type === "Picking" ){
                            $("#" + Print_Div + " table tbody").append("<tr>" +"<TD class='td1'>"+(i+1)+"</TD>"+ trHtml + "</tr>");
                        }else{
                            $("#" + Print_Div + " table tbody").append("<tr>" + trHtml + "</tr>");
                        }
                    }
                }
                //拼接tfoot

                var tfootHtml;
                if(type === "Receipt"){
                    tfootHtml = '<tr><TD class="td1">总计：</TD><TD class="td1" colspan="3"></TD>' +
                        '<TD class="td1" tdata="Sum" format="#.##">######</TD><TD class="td1" tdata="Average" format="#.00">均价:######</TD>' +
                        '<TD class="td1" tdata="Sum" format="#,##0.00">########</TD><TD class="td1"></TD></tr>'
                }else if(type === "StoreReceipt"){
                    tfootHtml = '<tr><TD class="td1">总计：</TD><TD class="td1" colspan="4"></TD>' +
                        '<TD class="td1" tdata="Sum" format="#.##">######</TD><TD class="td1" tdata="Sum" format="#.##">######</TD>' +
                        '<TD class="td1" tdata="Sum" format="#.##">######</TD><TD class="td1" tdata="Average" format="#.00">均价:######</TD>' +
                        '<TD class="td1" tdata="Sum" format="#,##0.00">########</TD><TD class="td1"></TD></tr>'
                }else if(type === "Shipment"){
                    tfootHtml = '<tr><TD class="td1">总计：</TD><TD class="td1" colspan="4"></TD>' +
                        '<TD class="td1" tdata="Sum" format="#.##">######</TD><TD class="td1" tdata="Sum" format="#.##">######</TD>' +
                        '<TD class="td1" tdata="Sum" format="#.##">######</TD><TD class="td1" tdata="Average" format="#.00">均价:######</TD>' +
                        '<TD class="td1" tdata="Sum" format="#,##0.00">########</TD><TD class="td1"></TD></tr>'
                }else if(type === "ReceiptCommon"){
                    tfootHtml = '<tr><TD class="td1">总计：</TD>' +
                        '<TD class="td1"></TD>' +
                        '<TD class="td1" tdata="Sum" format="#.##">######</TD>' +
                        '<TD class="td1"></TD>' +
                        '<TD class="td1" colspan="9"></TD>' +
                        '<TD class="td1" tdata="Sum" format="#.##">######</TD>' +
                        '<TD class="td4"></TD></tr>'
                }
                $("#" + Print_Div + " table tfoot").after(tfootHtml);
                $("#" + Print_Div + " table tbody tr").eq(0).remove();
            }
            LODOP.ADD_PRINT_TABLE(tableObj.ItemTop, tableObj.ItemLeft, tableObj.ItemWidth, tableObj.ItemHeight, document.getElementById(Print_Div).innerHTML)
            $("#" + Print_Div).empty();
           $("#" + Print_Detail).hide();
        }


        ///获取默认模板
        function _defaultTemplateById(id) {
            var template = null;
            $(WmsPrintTemplate).each(function () {
                if (this.id == id) {
                    template = this;
                }
            });
            return template;
        }

        function _defaultTemplate(code, carrierCode) {
            var template = null;
            $(WmsPrintTemplate).each(function () {
                if (this.reportCategoryCode == code && this.isDefault == 1) {
                    if (carrierCode) {
                        if (this.carrier == carrierCode) {
                            template = this;
                        }
                    } else {
                        template = this;
                    }
                }
            });
            if (template == null) {
                if (carrierCode) {
                    kendo.ui.ExtAlertDialog.showError(carrierCode + "-" + code + " - 不存在默认模板");
                } else {
                    kendo.ui.ExtAlertDialog.showError(code + " - 不存在默认模板");
                }
            }
            return template;
        }

        //获取当前环境所配置的打印机
        WmsPrint.prototype.getCurrentMachines = function () {
            return _machine();
        };

        //获取默认打印机
        WmsPrint.prototype.getDefaultMachine = function () {
            return _defaultMachine();
        };

        WmsPrint.prototype.getReportContent = function (key) {
            return _content(key);
        }

        if ($("#" + Print_Div).length <= 0) {
            $("body").append("<div id='" + Print_Div + "' style='display:none;'></div>");
        } else {
            $("#" + Print_Div).html("");
        }


        //打印类型
        WmsPrint.prototype.PrintType = {
            SkuBarcode: "SkuBarcode", //条码打印
            Location: "Location", //货位打印
            Receipt: "Receipt", //入库单打印
            ReceiptCommon: "ReceiptCommon", //通用入库单打印
            StoreReceipt: "StoreReceipt",//代储入库单打印
            Shipment: "Shipment" ,//代储出库单打印
            Picking: "Picking" //拣货单打印
        }

        /////////////////////////////////对外暴露接口开始/////////////////////////////////////
        WmsPrint.prototype.lodopCustom = function () {
            return _lodopCustom();
        }

        WmsPrint.prototype.setPageSize = function (ori, width, height, pageType) {
            return setPageSize(ori, width, height, pageType)
        }

        /**
         * 打印商品条码
         * @param ids
         * @param templateId 模板ID
         * @param copies 打印份数
         */
        WmsPrint.prototype.printSkuBarcode = function (printDatas, copies) {
            return printExec(printDatas, this.PrintType.SkuBarcode, _defaultTemplate(this.PrintType.SkuBarcode), copies);
        }


        /***
         * 打印入库单
         * @param printDatas
         * @param copies
         * @returns {{suc}|boolean}
         */
        WmsPrint.prototype.printReceipt = function (printDatas, copies,printingType) {
            if(printingType === "storeprint"){
                return printExec(printDatas, this.PrintType.StoreReceipt, _defaultTemplate(this.PrintType.StoreReceipt), copies);
            }else if(printingType === "print"){
                return printExec(printDatas, this.PrintType.Receipt, _defaultTemplate(this.PrintType.Receipt), copies);
            }else{
                return printExec(printDatas, this.PrintType.ReceiptCommon, _defaultTemplate(this.PrintType.ReceiptCommon), copies);
            }
        }

        /**
         * 打印出库单
         * @param printDatas
         * @param copies
         * @returns {{suc}|boolean}
         */
        WmsPrint.prototype.printShipment = function (printDatas, copies) {
            return printExec(printDatas, this.PrintType.Shipment, _defaultTemplate(this.PrintType.Shipment), copies);
        }

        /**
         * 打印拣货单
         * @param printDatas
         * @param copies
         * @returns {{suc}|boolean}
         */
        WmsPrint.prototype.printPicking = function (printDatas, copies) {
            return printExec(printDatas, this.PrintType.Picking, _defaultTemplate(this.PrintType.Picking), copies);
        }

        /////////////////////////////////对外暴露接口结束/////////////////////////////////////



        //绑定打印模板
        return new WmsPrint();
    }]);
});

