define(['app'], function (app) {
    'use strict';
    app.factory('initializeData', ['sync', function ($sync) {
        return {
            init: function (funcCallBack) {
                $sync(window.BASEPATH + "/code/all/details", "GET", {wait: false})
                    .then(function (xhr) {
                        if (xhr.result) {
                            var option = {};
                            for (var key in xhr.result) {
                                var codeDetail = xhr.result[key];
                                if (codeDetail) {
                                    var descOpt = [];
                                    for (var i = 0; i < codeDetail.length; i++) {
                                        var codeValue = codeDetail[i].codeValue;
                                        var codeName = codeDetail[i].codeName;
                                        var objectCode = {};
                                        objectCode.key = codeName;
                                        objectCode.value = codeValue;
                                        descOpt[i] = objectCode;
                                    }
                                    option[key] = descOpt;
                                }
                            }
                            window.WMS.CODE_SELECT_DATA = option;
                            funcCallBack();
                        }
                    })
                    .then(function () {
                        if (window.WMS.WAREHOUSE_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/warehouse", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.WAREHOUSE_DATA = xhr.result;
                                }
                            });
                        }
                    })
                    .then(function () {
                        if (window.WMS.SKU_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/categorys", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.SKU_DATA = xhr.result;
                                }
                            });
                        }
                    })
                    .then(function () {
                        if (window.WMS.ORGANIZATIONS_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/organizations", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.ORGANIZATIONS_DATA = xhr.result;
                                }
                            });
                        }
                    })
                    .then(function () {
                        if (window.WMS.ZONE_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/zone", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.ZONE_DATA = xhr.result;
                                }
                            });
                        }
                    })
                    .then(function () {
                        if (window.WMS.LOCATION_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/location", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.LOCATION_DATA = xhr.result;
                                }
                            });
                        }
                    })
                    .then(function () {
                        if (window.WMS.SKUCATEGORYS_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/skuCategorys/0", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.SKUCATEGORYS_DATA = xhr.result;
                                }
                            });
                        }
                    })
                    .then(function () {
                        if (window.WMS.CARGO_OWNER_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/cargoOwner", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.CARGO_OWNER_DATA = xhr.result;
                                }
                            });
                        }
                    })
                    .then(function () {
                        if (window.WMS.STORAGEROOM_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/storageRoom", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.STORAGEROOM_DATA = xhr.result;
                                }
                            });
                        }
                    })
                    .then(function () {
                        if (window.WMS.PRINT_MAP_DATA === undefined) {
                            $sync(window.BASEPATH + "/index/printMap", "GET", {wait: false}).then(function (xhr) {
                                if (xhr.result) {
                                    window.WMS.PRINT_MAP_DATA = xhr.result;
                                }
                            });
                        }
                    })


            }
        }
    }]);
});