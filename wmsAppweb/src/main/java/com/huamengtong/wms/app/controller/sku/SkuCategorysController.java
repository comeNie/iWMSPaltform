package com.huamengtong.wms.app.controller.sku;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.TWmsSkuCategorysDTO;
import com.huamengtong.wms.entity.sku.TWmsSkuCategorysEntity;
import com.huamengtong.wms.sku.service.ISkuCategorysService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sku/categorys")
public class SkuCategorysController extends BaseController {

    @Autowired
    private ISkuCategorysService skuCategorysService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getSkuCategoryslist(TWmsSkuCategorysDTO skuCategorysDTO) throws Exception{
           skuCategorysDTO.setParentId(0L);
          return  getSucResultData(skuCategorysService.querySkuCategorysPages(skuCategorysDTO,getDbShardVO(DbShareField.SKU)));
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseResult getSkuCategoryslist(@PathVariable Long id,@RequestParam Map map) throws Exception{
        List<TWmsSkuCategorysEntity> skuCategorysEntityList = skuCategorysService.findByParentId(id,getDbShardVO(DbShareField.SKU));
        Map resultMap = new HashedMap();
        resultMap.put("rows",skuCategorysEntityList);
        resultMap.put("total",skuCategorysEntityList.size());
        return  getSucResultData(resultMap);
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifySkuCategorys(@RequestBody TWmsSkuCategorysDTO skuCategorysDTO) throws  Exception{
        skuCategorysDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        skuCategorysDTO.setUpdateTime(new Date().getTime());
        return getMessage(skuCategorysService.modifySkuCategorys(skuCategorysDTO,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "/{parentId}/{id}",method = RequestMethod.PUT)
    public ResponseResult modifyDetailSkuCategorys(@RequestBody TWmsSkuCategorysDTO skuCategorysDTO) throws  Exception{
        skuCategorysDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        skuCategorysDTO.setUpdateTime(new Date().getTime());
        return getMessage(skuCategorysService.modifySkuCategorys(skuCategorysDTO,getDbShardVO(DbShareField.SKU)));
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeSkuCategorys(@PathVariable Long id) throws  Exception{
        return getMessage(skuCategorysService.removeByPrimaryKey(id,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "/{parentId}/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeDetailSkuCategorys(@PathVariable Long id) throws  Exception{
        return getMessage(skuCategorysService.removeByPrimaryKey(id,getDbShardVO(DbShareField.SKU)));
    }


    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult createLocation(@RequestBody TWmsSkuCategorysDTO skuCategorysDTO) throws  Exception{
        skuCategorysDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        skuCategorysDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        skuCategorysDTO.setCreateTime(new Date().getTime());
        skuCategorysDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        skuCategorysDTO.setUpdateTime(new Date().getTime());
        return getMessage(skuCategorysService.createSkuCategorys(skuCategorysDTO,getDbShardVO(DbShareField.SKU)));

    }


}
