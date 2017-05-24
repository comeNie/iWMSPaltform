define([ 'app', 'kendo', 'scripts/common/commonService'], function (app, kendo, commonService) {
    "use strict";
    app.controller('mainController', ['$scope', '$rootScope', '$location', '$route', 'sync', 'url', function ($scope, $rootScope, $location, $route, sync, url) {

        $rootScope.location = $location;

        //加载版本信息
        $.getJSON(window.BASEPATH + "/app/data/basic/version.json", function (data) {
            var versionDes = data.smartVersion;
            var array = _.sortBy(versionDes, function(num){
                return num;
            });
            array.reverse();
            //版本号设置
            $rootScope.versionNo = array[0].versionNo;
            $rootScope.versionDes = array;
        }, function (data) {

        });

        var path = '';
        $rootScope.$on("kendoWidgetCreated", function (event, widget) {
            if (_.isObject(widget.options)) {
                // 取画面上第一个被创建的grid组件作为主grid
                if (widget.options.name === "Grid" && path !== $rootScope.location.path()) {
                    path = $rootScope.location.path();
                    WMS.UTILS.resetTableHeight(!!widget.options.toolbar, widget.options.hasFooter, widget.options.hasTabHeader);
                }
            }
        });

        ////所有页面导出
        $rootScope.exportExcelAll = function (e) {
            var grid = $(e.target).parents('[kendo-grid]').eq(0).data("kendoGrid"),
                url = grid.dataSource.options.url,
                paras = commonService.exportOperator.makePara(grid, url, $rootScope.title + '.xlsx');
            sync(window.BASEPATH + "/excel/all", "POST", {
                data: paras,
                responseType: 'arraybuffer',
                fileName: $rootScope.title + '.xlsx'
            });
        };

        /**
         * 导出Excel,主要用于选中一条主表数据,导出所有子表明细
         * @param colDefine 列定义json
         * @param url
         * @param fileName
         * @param filter  键值对json对象，用来拼接在url上面过滤数据,var filter = {receivedQty: 1, lotKey: "A_5054_6159", "locationId": 1};
         * @returns {*}
         */
        $rootScope.exportExcelAll_Detail = function (colDefine, url, fileName, filter) {
            var paras = commonService.exportOperator.makePara_Detail(colDefine, url, fileName, 1, 20000, 20000, filter);
            sync(window.BASEPATH + "/excel/all", "POST", {
                data: paras,
                responseType: 'arraybuffer',
                fileName: fileName
            });
        }

        //////////修改密码///////////////////////////////////////
        $rootScope.updatePwd = function () {
            var passwordWindow = $('#pwdWin').data("kendoWindow");
            passwordWindow.setOptions({
                modal: true,
                width: 350,
                title: '修改密码',
                actions: ["Close"]
            });
            passwordWindow.center().open();
        };
        $rootScope.modifyPwd = function ($event) {
            $event.preventDefault();
            var form = $($event.target);
            var formValidator = form.kendoValidator().data("kendoValidator");
            if (!formValidator.validate()) {
                return;
            }
            var params = $($event.target).serializeJSON();
            sync(url.passwordUrl, "PUT", {
                data: {
                    passwordOld: params.old_password,
                    password: params.new_password
                }
            }).then(function () {
                $scope.closePwdWin();
            });
        };
        $rootScope.closePwdWin = function () {
            $('#pwdWin').data("kendoWindow").wrapper.find('input').val('');
            $('#pwdWin').data("kendoWindow").close();
        };
        // 临时解决方案，不能调用ng-click
        $('body').on('submit', '#pwdForm', function (e) {
            e.preventDefault();
            $rootScope.modifyPwd(e);
        });
        $('body').on('click', '#pwdCloseBtn', function (e) {
            e.preventDefault();
            $rootScope.closePwdWin();
        });

        // 切换仓库
        $rootScope.openWarehouseWin = function (isSwitch) {
            sync(url.getWarehouseUrl, "GET", {wait: false}).then(function (resp) {
                var list = resp.result.list;
                if (!resp.result.selected || isSwitch) {
                    $rootScope.warehouseList = list;
                    var switchWarehouseWin = $('#switchWarehouseWin').kendoWindow({
                        width: '620px',
                        height: '370px',
                        title: '仓库选择',
                        actions: ["Close"],
                        //content: './tmpl/popup/switchWarehouse.html',
                        modal: true
                    }).data("kendoWindow");
                    switchWarehouseWin.center().open();
                } else {
                    var warehouse = _.findWhere(list, {value: resp.result.selected});
                    $rootScope.user.warehouse = warehouse.key;
                }
            });
        };
        $rootScope.switchWarehouse = function (warehouse) {
            sync(url.switchWarehouseUrl + "/" + warehouse.value, "PUT", {wait: false}).then(function (resp) {
                $rootScope.user.warehouse = warehouse.key;
                if ($('#switchWarehouseWin').data("kendoWindow")) {
                    $('#switchWarehouseWin').data("kendoWindow").close();
                }
                //$location.path("/");
                window.location.href = window.BASEPATH + "/app/index.html";
            });
        };

      }
    ]);
});
