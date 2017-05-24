package com.huamengtong.wms.app.controller.outwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.outwh.TWmsWaveDTO;
import com.huamengtong.wms.outwh.service.IWaveService;
import com.huamengtong.wms.utils.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by could.hao on 2016/12/19.
 */
@RestController
@RequestMapping("/wave")
public class WaveController extends BaseController {

    @Autowired
    IWaveService waveService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getWaveList(TWmsWaveDTO waveDTO)throws Exception{
        return getSucResultData(waveService.getPage(waveDTO,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "{id}/detail",method = RequestMethod.GET)
    public ResponseResult getWaveDetail(@PathVariable Long id)throws Exception{
        return getSucResultData(waveService.getShipmentHeaderPage(id,getDbShardVO(DbShareField.OUT_WH)));
    }

    @RequestMapping(value = "/printShipment/{ids}",method = RequestMethod.GET)
    public ResponseResult getPrintShipmentHeaderIds(@PathVariable String ids)throws Exception{
        Long[] idArr = ArrayUtil.toLongArray(ids.split(","));
        return getSucResultData(waveService.getShipmentIds(idArr,getDbShardVO(DbShareField.OUT_WH)));
    }
}
