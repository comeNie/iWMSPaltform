package com.huamengtong.wms.app.controller.sku;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.dto.sku.TWmsSkuDTO;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.ExcelReaderUtil;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sku")
@Api(value = "/sku",description = "商品管理")
public class SkuController extends BaseController {

    @Autowired
    private ISkuService skuService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseResult getSkuList(TWmsSkuDTO skuDTO)throws Exception{
        skuDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        return getSucResultData(skuService.querySkuPages(skuDTO,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    public ResponseResult getSkuMap(TWmsSkuDTO skuDTO)throws Exception{
        return getSucResultData(skuService.querySkuPages(skuDTO,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseResult addSku(@RequestBody TWmsSkuDTO skuDTO)throws Exception{
        Byte b = 1;
        skuDTO.setIsActive(b);
        skuDTO.setTenantId(this.getSessionCurrentUser().getTenantId());
        skuDTO.setCreateUser(this.getSessionCurrentUser().getUserName());
        skuDTO.setCreateTime(new Date().getTime());
        skuDTO.setUpdateTime(new Date().getTime());
        skuDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        return getMessage(skuService.createSku(skuDTO,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseResult modifySku(@RequestBody TWmsSkuDTO skuDTO)throws Exception {
        skuDTO.setUpdateUser(this.getSessionCurrentUser().getUserName());
        skuDTO.setUpdateTime(new Date().getTime());
        return getMessage( skuService.updateSku(skuDTO,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult removeSku(@PathVariable Long id) throws Exception {
        return getMessage(skuService.removeSku(id,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "/print/{ids}",method = RequestMethod.GET)
    public ResponseResult printSku(@PathVariable String ids) {
        List<TWmsSkuEntity> skuEntityLists = skuService.findSkuLists(ids,getDbShardVO(DbShareField.SKU));
        Map resultMap = new HashMap();
        resultMap.put("rows",skuEntityLists);
        return getSucResultData(resultMap);
    }

    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public ResponseResult importSkuFromUpload(@RequestParam("file") MultipartFile file) throws Exception {
        if(!file.isEmpty()){
           List list = ExcelReaderUtil.getExcelData(file.getInputStream());
           return getMessage(skuService.updateSkuUpLoad(list,getDbShardVO(DbShareField.SKU)));
        }
        return getFaultMessage("E00016");
    }

    @RequestMapping(value = "/batch/{ids}",method = RequestMethod.DELETE)
    public ResponseResult removeSku(@PathVariable String ids)throws Exception{
        return getMessage(skuService.removeSkuBatch(ids,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "/data/cargoOwner/{cargoOwnerId}",method = RequestMethod.GET)
    public ResponseResult getSkuListByCargoOwnerId(@PathVariable Long cargoOwnerId,TWmsSkuDTO skuDTO)throws Exception{
        skuDTO.setCargoOwnerId(cargoOwnerId);
        return getSucResultData(skuService.querySkuPagesByCargoOwner(skuDTO,getDbShardVO(DbShareField.SKU)));
    }

    @RequestMapping(value = "/data/{sku}",method = RequestMethod.GET)
    public ResponseResult getSkuListBySku(@PathVariable String sku, TWmsSkuDTO skuDTO)throws Exception {
        skuDTO.setSku(sku);
        return getSucResultData(skuService.querySkuPages(skuDTO,getDbShardVO(DbShareField.SKU)));
    }

    }

