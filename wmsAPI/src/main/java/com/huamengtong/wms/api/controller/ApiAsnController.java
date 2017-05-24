package com.huamengtong.wms.api.controller;

import com.huamengtong.wms.api.controller.common.ApiBaseController;
import com.huamengtong.wms.api.dto.CreateAsnDTO;
import com.huamengtong.wms.api.remote.service.IAsnRemoteService;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.respoes.ApiResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * 入库通知单接口对外API
 */
@RestController
@RequestMapping("api/asn")
public class ApiAsnController extends ApiBaseController {

    @Autowired
    IAsnRemoteService asnRemoteService;
    /***
     * 创建商品信息API
     * @param asnDTOList
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ApiResponseResult createAsn(@RequestBody List<CreateAsnDTO> asnDTOList) throws Exception {
        MessageResult result = asnRemoteService.createAsn(asnDTOList);
        if(!result.isSuc()) {
            return this.getApiErrMessage("E00001");
        }
        return this.getApiSucMessage();
    }
}
