/***
 * 过滤器,用来处理Grid中列表格式化数据
 * 通过codeData.js 初始化数据，使用时先加载数据
 * codeFormat 为数据字典里面的keyVals
 * whFormat 仓库
 */
define(['app'], function (app) {
    'use strict';
    app.filter('codeFormat', function () {
        return function (input, codeType) {
            var codeArr = window.WMS.CODE_SELECT_DATA[codeType];
            var txt = '';
            if (codeArr !== undefined) {
                for (var i = 0; i < codeArr.length; i++) {
                    var data = codeArr[i];
                    if (data.value == input) {
                        txt = data.key;
                    }
                }
            }
            return txt;
        };
    });

    app.filter('whFormat', function () {
        return function (input) {
            var whArr = window.WMS.WAREHOUSE_DATA;
            var txt = input;
            if (whArr !== undefined) {
                for (var i = 0; i < whArr.length; i++) {
                    if (whArr[i].value == input) {
                        txt = whArr[i].key;
                    }
                }
            }
            return txt;
        };
    });

    app.filter('locationFormat', function () {
        return function (input) {
            var stArr = window.WMS.LOCATION_DATA;
            var txt = input;

            if (stArr !== undefined) {
                for (var i = 0; i < stArr.length; i++) {
                    if (stArr[i].value == input) {
                        txt = stArr[i].key;
                    }
                }
            }
            return txt;
        };
    });

    app.filter('zoneFormat', function () {
        return function (input) {
            var stArr = window.WMS.ZONE_DATA;
            var txt = input;

            if (stArr !== undefined) {
                for (var i = 0; i < stArr.length; i++) {
                    if (stArr[i].value == input) {
                        txt = stArr[i].key;
                    }
                }
            }
            return txt;
        };
    });

    app.filter('skuCategorysFormat', function () {
        return function (input) {
            var stArr = window.WMS.SKUCATEGORYS_DATA;
            var txt = input;

            if (stArr !== undefined) {
                for (var i = 0; i < stArr.length; i++) {
                    if (stArr[i].value == input) {
                        txt = stArr[i].key;
                    }
                }
            }
            return txt;
        };
    });

    app.filter('cargoOwnerFormat', function () {
        return function (input) {
            var stArr = window.WMS.CARGO_OWNER_DATA;
            var txt = input;

            if (stArr !== undefined) {
                for (var i = 0; i < stArr.length; i++) {
                    if (stArr[i].value == input) {
                        txt = stArr[i].key;
                    }
                }
            }
            return txt;
        };
    });

    app.filter('organizationsFormat', function () {
        return function (input) {
            var stArr = window.WMS.ORGANIZATIONS_DATA;
            var txt = input;

            if (stArr !== undefined) {
                for (var i = 0; i < stArr.length; i++) {
                    if (stArr[i].value == input) {
                        txt = stArr[i].key;
                    }
                }
            }
            return txt;
        };
    });

    app.filter('storageRoomFormat', function () {
        return function (input) {
            var stArr = window.WMS.STORAGEROOM_DATA;
            var txt = input;

            if (stArr !== undefined) {
                for (var i = 0; i < stArr.length; i++) {
                    if (stArr[i].value == input) {
                        txt = stArr[i].key;
                    }
                }
            }
            return txt;
        };
    });

    app.filter('storageRoomStringFormat', function () {
        return function (input) {
            if(input==null){
                return ;
            }
            var stArr = window.WMS.STORAGEROOM_DATA;
            var arrTxt = input.split(",");
            var txtAll = null;
            for(var j = 0 ;j < arrTxt.length;j ++ ){
                var txt = arrTxt[j];

                if (stArr !== undefined) {
                    for (var i = 0; i < stArr.length; i++) {
                        if (stArr[i].value == txt) {
                            if(txtAll !== null){
                                txtAll += ","+stArr[i].key;
                            }else{
                                txtAll = stArr[i].key;
                            }

                        }
                    }
                }
            }

            return txtAll;
        };
    });

    app.filter('printMapFormat', function () {
        return function (input) {
            var stArr = window.WMS.PRINT_MAP_DATA;
            var txt = input;

            if (stArr !== undefined) {
                for (var i = 0; i < stArr.length; i++) {
                    if (stArr[i].value == input) {
                        txt = stArr[i].key;
                    }
                }
            }
            return txt;
        };
    });



    app.filter('dateFilter', function () {
        return function (input) {
            if(input == 0){
                return null;
            }
            return input;
        };
    });

    app.filter('yesOrNoFormat', ['$filter', function ($filter) {
        return function (input) {
            var txt = "否";
            if (input === '1' || input === true) {
                txt = "是";
            }
            return txt;
        };
    }]);

    app.filter('dataIgnore', ['$filter', function ($filter) {
        return function (input) {
            if(input === '1970-01-01 08:00:00'){
                return "";
            }
            return input;
        };
    }]);
});