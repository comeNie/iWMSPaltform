
define(['scripts/controller/controller', '../../model/system/printTempModel'], function (controller, printTemp) {
    "use strict";
    controller.controller('printTempController',['$scope', '$rootScope', 'sync', 'url', 'wmsDataSource','wmsPrint',
        function($scope, $rootScope, $sync, $url, wmsDataSource,wmsPrint) {

            //打开模板设计时初始化
            var LODOP;
            $scope.pageOriAll = [
                {value: "0", key: "方向不定"},
                {value: "1", key: "纵向,固定纸张"},
                {value: "2", key: "横向,固定纸张"},
                {value: "3", key: "纵向,宽度固定"}
            ];
            var printTempHeaderUrl = $url.systemPrintTempUrl,
                printTempHeaderColumns = [
                    { title: '操作', command: [
                        { name: "edit", template: "<a class='k-button k-button-custom-command' href='\\#' ng-click='editTemplate(dataItem);' >编辑</a>",
                            className: "btn-auth-edit", text: { edit: "编辑", cancel: "取消", update: "更新" } },
                        WMS.GRIDUTILS.deleteButton
                    ],width: "100px"},
                    { title: '所属平台', align: 'left',field: 'projectCode', width: "120px"},
                    { title: '模板名称', align: 'left',field: 'reportName',  width: "120px"},
                    { title: '模版映射', field: 'reportCategoryCode',filterable: false,template: WMS.UTILS.printMapFormat, align: 'left', width: "120px"},
                    { title: '模板内容', align: 'left',field: 'content', width: "200px",template:function(dataItem) { return WMS.UTILS.tooLongContentFormatOnleyForPrint(dataItem,"content") }},
                    { title: '是否默认', align: 'left',field: 'isDefault', width: "120px",template:WMS.UTILS.checkboxAuthTmp("isDefault")},
                ],
                printTempHeaderDataSource = wmsDataSource({
                    url:printTempHeaderUrl,
                    schema: {
                        model: printTemp.printTempHeader
                    }, callback: {
                        update: function (response, editData) {
                            $scope.mainGridOptions.dataSource.read();
                        }
                    }
                });
            printTempHeaderColumns = printTempHeaderColumns.concat(WMS.UTILS.CommonColumns.defaultColumns);

            $scope.mainGridOptions = WMS.GRIDUTILS.getGridOptions({
                dataSource: printTempHeaderDataSource,
                toolbar: [{template: '<a class="k-button k-grid-addTemplate" ng-click="addTemplate()" href="\\#">新增</a>', className: "btn-auth-addTemplate"}],
                columns: printTempHeaderColumns,
                editable: {
                    mode: "popup",
                    window:{
                        width:"400"
                    },
                }
            }, $scope);

            //获取打印机
            $scope.printer = '';
            try {
                if (navigator.userAgent.indexOf("Mac") < 0) {
                    $scope.allPrinter = wmsPrint.getCurrentMachines();
                    var defPrinter = wmsPrint.getDefaultMachine();
                    if (defPrinter) {
                        $scope.printer = {key: defPrinter, value: defPrinter};
                    }
                }
            } catch (e) {
                console.log(e);
            }


            /***
             * 模板新增
             */
            $scope.addTemplate = function () {
                $scope.templateModel = {};
                $scope.templatePopup.refresh().open().center();
            };


            /***
             * 自定义模板编辑页面
             * @param data
             */
            $scope.editTemplate = function (data) {
                $scope.printTemp = {};
                $scope.printTemp.id = data.id;
                $scope.printTemp.reportName = data.reportName;
                $scope.printTemp = data;
                //模板映射赋值
                $("#reportCategoryCode").children().scope().has_selected_printMapSrcreportCategoryCode = {key: reportName, value: data.reportCategoryCode};
                var options = JSON.parse(data.printOptions);
                $scope.printTemp.isDefault = data.isDefault;
                $scope.printTemp.pageLength = options.pageLength;
                $scope.printTemp.pageWidth = options.pageWidth;
                $scope.printTemp.pageOri = {value: options.pageOri};
                $scope.printTemp.printMachine = options.printMachine;
                $scope.templatePopup.refresh().open().center();
            };

            /***
             * 关闭模板
             */
            $scope.templateClose = function () {
                $scope.templatePopup.close();
            };


            /**
             * 保存模版
             */
            $scope.saveTemplate = function (e) {
                var formValidator = $(e.target).parents(".k-edit-form-container").kendoValidator({ validateOnBlur: false }).data("kendoValidator");
                if (!formValidator.validate()) {
                    return;
                }

                var pageOriVal;
                if ($scope.printTemp.pageOri) {
                    pageOriVal = $scope.printTemp.pageOri.value;
                } else {
                    pageOriVal = "";
                }
                var params = {
                    "id": $scope.printTemp.id,
                    "reportName": $scope.printTemp.reportName,
                    "reportCategoryCode":$('#reportCategoryCode').val(),
                    "projectCode":$scope.printTemp.projectCode,
                    "isDefault": $scope.printTemp.isDefault,
                    "content": $scope.printTemp.content,
                    "printOptions": JSON.stringify({pageLength: $scope.printTemp.pageLength, pageWidth: $scope.printTemp.pageWidth, pageOri: pageOriVal, printMachine: $scope.printTemp.printMachine})
                };
                if ($scope.printTemp.id) {
                    //更新模版信息
                    $sync(window.BASEPATH + "/print/temp/"+$scope.printTemp.id, "PUT", {data: params})
                        .then(function (xhr) {
                            $scope.printTemp = {};
                            $scope.templatePopup.close();
                            $scope.printTempHeaderGrid.dataSource.read();
                        }, function (xhr) {
                            $scope.mainGridOptions.dataSource.read();
                        });
                } else {
                    //保存模版信息
                    $sync(window.BASEPATH + "/print/temp", "POST", {data: params})
                        .then(function (xhr) {
                            $scope.printTemp = {};
                            $scope.templatePopup.close();
                            $scope.printTempHeaderGrid.dataSource.read();
                        }, function (xhr) {
                            $scope.mainGridOptions.dataSource.read();
                        });
                }

            };


            /**
             * 设置模版
             * @param e
             */
            $scope.setTemplate = function (e) {
                toDesignTemplate();
            }



            /***
             * 模版设置器
             */
            function toDesignTemplate() {
                //获得LODOP对象 并初始化
                LODOP = wmsPrint.lodopCustom();
                var printModelName = $('#reportName').val();
                LODOP.PRINT_INIT(printModelName + "的模板");

                if ($('#pageLength').val() != '' || $('#pageWidth').val() != '') {
                    var height = $('#pageLength').val();
                    var width = $('#pageWidth').val();
                    wmsPrint.setPageSize($('#pageLength').val(), width, height, "");
                }
                var content = $scope.printTemp.content;
                if (content != '' && content != null && content !== undefined) {

                    designPrintByContent(content, wmsPrint.getReportContent($('#reportCategoryCode').val()))

                } else {
                    var type = $('#reportCategoryCode').val();

                    var obj = wmsPrint.getReportContent(type);

                    if (obj == undefined) {
                        kendo.ui.ExtAlertDialog.showError("未获取到当前模版" + printModelName + "的配置信息,请检查模版类型是否有效!");
                        return;
                    }
                    var field = obj.field;
                    designPrint(JSON.parse(field), obj.detailDiv);

                }
                var contentObj = makeTemplateObj();
                $scope.printTemp.content = JSON.stringify(contentObj);
                $('#content').val(JSON.stringify(contentObj));

            }

            /**
             * 组织模版数据
             * @returns {{}}
             */
            function makeTemplateObj() {
                var count = LODOP.GET_VALUE('ItemCount', 0);
                var contentObj = {};
                var itemArr = [];
                for (var i = 0; i <= count; i++) {
                    var className = LODOP.GET_VALUE('ItemClassName', i);
                    if (className == '') {
                        continue;
                    }

                    var item = {
                        className: className,
                        ItemTop: LODOP.GET_VALUE('ItemTop', i),
                        ItemLeft: LODOP.GET_VALUE('ItemLeft', i),
                        ItemWidth: LODOP.GET_VALUE('ItemWidth', i),
                        ItemHeight: LODOP.GET_VALUE('ItemHeight', i),
                        index: LODOP.GET_VALUE('ItemNameID', i)
                    };

                    var styleA = {};
                    if (className != 'Table' && className != 'Htm') {
                        item.ItemContent = LODOP.GET_VALUE('ItemContent', i)
                        styleA.ItemType = LODOP.GET_VALUE('ItemPageType', i);      //普通 眉脚等
                        styleA.FontName = LODOP.GET_VALUE('ItemFontName', i);//条形码类型
                        styleA.FontSize = LODOP.GET_VALUE('ItemFontSize', i);
                        styleA.FontColor = LODOP.GET_VALUE('ItemColor', i);
                        styleA.showBarCode = LODOP.GET_VALUE('ItemShowBarText', i);//条形码是否显示文本

                        styleA.PreviewOnly = LODOP.GET_VALUE('ItemPreviewOnly', i);//仅用来预览
                        styleA.Repeat = LODOP.GET_VALUE('ItemPRepeat', i);        //仅用来预览

                        styleA.Alignment = LODOP.GET_VALUE('ItemAlign', i);
                        styleA.Bold = LODOP.GET_VALUE('Itembold', i);
                        styleA.Italic = LODOP.GET_VALUE('ItemItalic', i);
                        styleA.Underline = LODOP.GET_VALUE('ItemUnderline', i);

                        styleA.PenWidth = LODOP.GET_VALUE('ItemPenWidth', i);             //打印项线条宽度
                        styleA.PenStyle = LODOP.GET_VALUE('ItemPenStyle', i);             //打印项线条类型
                        styleA.Horient = LODOP.GET_VALUE('ItemHorient', i);               //打印项左右位置
                        styleA.Vorient = LODOP.GET_VALUE('ItemVorient', i);               //打印项左右位置


                        styleA.LineSpacing = LODOP.GET_VALUE('ItemLineSpacing', i);           //打印项行间距
                        styleA.LetterSpacing = LODOP.GET_VALUE('ItemLetterSpacing', i);       //打印项字间距
                        styleA.ItemGroundColor = LODOP.GET_VALUE('ItemGroundColor', i);
                        styleA.AlignJustify = LODOP.GET_VALUE('ItemAlignJustify', i);        //该打印项文本两端是否靠齐
                        styleA.SpacePatch = LODOP.GET_VALUE('ItemSpacePatch', i);          //该打印项文本尾是否补空格

                        styleA.TextFrame = LODOP.GET_VALUE('ItemTextFrame', i);               //该打印项边框类型
                        styleA.ItemLetterSpacing = LODOP.GET_VALUE('ItemSpacePatch', i);    //该打印项文本尾是否补空格
                        styleA.ItemGroundColor = LODOP.GET_VALUE('ItemAlignJustify', i);    //该打印项文本两端是否靠齐


                        styleA.ItemLineSpacing = LODOP.GET_VALUE('ItemTranscolor', i);       //该打印项图片透明背景色
                        styleA.ItemTop2Offset = LODOP.GET_VALUE('ItemTop2Offset', i);        //该打印项次页上边距偏移
                        styleA.ItemLeft2Offset = LODOP.GET_VALUE('ItemLeft2Offset', i);    //该打印项次页左边距偏移

                        styleA.ItemTranscolor = LODOP.GET_VALUE('ItemTranscolor', i);       //该打印项图片透明背景色
                        styleA.LinkedItem = LODOP.GET_VALUE('ItemLinkedItem', i);        //该打印项的关联对象的类名（或识别号）
                    } else {

                        if (className == 'Htm') {
                            var strIn = LODOP.GET_VALUE('ItemContent', i);
                            strIn = strIn.replace(/\\n/g, "<br/>");
                            strIn = strIn.replace(/\\"/g, '"');
                            strIn = strIn.replace(/<br\/>/g, "\n");
                            strIn = strIn.replace(/\\r /g, "");
                            strIn = strIn.replace(/\\r/g, "");
                            item.ItemContent = strIn;

                        } else if (className == 'BarCode') {
                            item.barCode = '128A';
                        }

                        item.ItemTableHeightScope = LODOP.GET_VALUE('ItemTableHeightScope', i);       //该打印项表格高是否含头脚
                    }

                    item.styleA = styleA;

                    itemArr.push(item);
                }

                contentObj.item = itemArr;
                return contentObj;
            }


            //新建设计模版
            function designPrint(field, detailDiv) {
                var top = 10, left = 10;
                for (var i = 0; i < field.length; i++) {
                    top += 20;
                    left += 20;
                    if (field[i].type == 'barcode') {
                        var codeType = '128A';
                        if (field[i].codeType !== undefined) {
                            codeType = field[i].codeType;
                        }
                        //TODO 条码值现在默认使用128A,后面根据需求调整
                        LODOP.ADD_PRINT_BARCODE(28, 34, 307, 47, "128A", field[i].cn);
                    } else {
                        LODOP.ADD_PRINT_TEXT(top, left, 75, 20, field[i].cn);
                    }
                }


                if (detailDiv !== undefined) {
                    LODOP.ADD_PRINT_HTM(0, 0, 750, 500, document.getElementById(detailDiv).innerHTML);
                }
                LODOP.SET_SHOW_MODE("SHOW_SCALEBAR", 1);
                var content = LODOP.PRINT_DESIGN();
                return content;
            }

            //编辑模版
            function designPrintByContent(contentObj, typeObj) {
                contentObj = JSON.parse(contentObj);
                var itemArr = contentObj.item;
                var linkedItemObj = {};
                if (itemArr) {
                    for (var i = 0; i < itemArr.length; i++) {
                        var item = itemArr[i];
                        var className = item.className;
                        var styleA = item.styleA;
                        if (className == 'Text') {
                            LODOP.ADD_PRINT_TEXT(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, item.ItemContent);
                            for (var id in styleA) {
                                if (styleA[id] != '' && styleA[id] != 0 && styleA[id] != '#000000') {
                                    if (id == 'LinkedItem') {
                                        linkedItemObj[item.index] = styleA[id]
                                    } else {
                                        LODOP.SET_PRINT_STYLEA(0, id, styleA[id]);
                                    }
                                }
                            }
                        } else if (className == 'Htm') {
                            LODOP.ADD_PRINT_HTM(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, item.ItemContent);
                        } else if (className == 'BarCode') {
                            LODOP.ADD_PRINT_BARCODE(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, styleA.FontName, item.ItemContent);
                            if (!styleA.showBarCode) {//条形码显示文字开关
                                LODOP.SET_PRINT_STYLEA(0, "ShowBarText", 0);
                            }
                        } else if (className == 'Rect') {
                            LODOP.ADD_PRINT_RECT(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, 0, 1);
                        }
                        else if (className == 'Image') {
                            LODOP.ADD_PRINT_IMAGE(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, item.ItemContent);
                        }
                        else {
                            if (typeObj.detailDiv != null) {
                                LODOP.ADD_PRINT_TABLE(item.ItemTop, item.ItemLeft, item.ItemWidth, item.ItemHeight, document.getElementById(typeObj.detailDiv).innerHTML);
                            }
                        }
                    }

                }
                for (var id in linkedItemObj) {
                    LODOP.SET_PRINT_STYLEA(id, "LinkedItem", linkedItemObj[id]);
                }
                LODOP.PRINT_DESIGN();

            }
        }
    ]);
})
