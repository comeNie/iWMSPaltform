package com.huamengtong.wms.app.controller.outwh;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.inventory.service.IProInventoryService;
import com.huamengtong.wms.vo.ProducerProcessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Edwin on 2017/1/6.
 */
@RestController
@RequestMapping(value = "/producer")
public class ProducerController  extends BaseController {

    @Autowired
    IProInventoryService proInventoryService;

    @RequestMapping(value = "/process",method = RequestMethod.GET)
    public Object getProducerList(@RequestParam Map map) throws  Exception{
       List<ProducerProcessVO> producerProcessVOList = proInventoryService.getProInventoryReport(map,getDbShardVO(DbShareField.IN_WH));
        Map resultMap = new HashMap();
        resultMap.put("rows",producerProcessVOList);
        resultMap.put("total",producerProcessVOList.size());
        return  getSucResultData(resultMap);
    }

    @RequestMapping(value = "/process/create",method = RequestMethod.POST)
    public ResponseResult addProducer(@RequestParam Map paramMap) throws  Exception{
        return getSucMessage();
    }
}
