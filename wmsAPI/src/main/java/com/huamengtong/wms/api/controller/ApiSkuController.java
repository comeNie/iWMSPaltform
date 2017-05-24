package com.huamengtong.wms.api.controller;

import com.huamengtong.wms.api.controller.common.ApiBaseController;
import com.huamengtong.wms.api.dto.CreateSkuDTO;
import com.huamengtong.wms.api.remote.service.ISkuRemoteService;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.respoes.ApiResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * 商品对外接口API
 */
@RestController
@RequestMapping("api/sku")
public class ApiSkuController extends ApiBaseController {

    @Autowired
    ISkuRemoteService skuRemoteService;

    /***
     * 创建商品信息API
     * @param list
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ApiResponseResult createSku(@RequestBody List<CreateSkuDTO> list) throws Exception {
        MessageResult result = skuRemoteService.createSku(list,getDefaultDbShardVO(DbShareField.SKU));
        if(!result.isSuc()) {
            return this.getApiErrMessage("E00001");
        }
        return this.getApiSucMessage();
    }

}
