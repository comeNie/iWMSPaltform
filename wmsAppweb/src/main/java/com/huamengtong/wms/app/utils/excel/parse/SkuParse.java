package com.huamengtong.wms.app.utils.excel.parse;

import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.parse.BasicParse;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.CharPool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class SkuParse extends BasicParse {
    private ISkuService skuService;
    private CodeParse codeParse;
    private DbShardVO dbShardVO;
    private List<TWmsSkuEntity> skuList;
    private Set <Long>sourceDataList = new HashSet();
    private static Map<String,String> convertMap = new HashMap();

    static {
        convertMap.put("skuItemName","itemName");
        convertMap.put("skuBarcode","barcode");
        convertMap.put("skuDescription","description");
        convertMap.put("skuSku","sku");
    }

    @Override
    public Object parseValue(Map item,String field, String format) {
        String value = MapUtils.getString(item,"skuId");
        Object skuProperty = "";
        skuProperty = getSkuProperty(Long.parseLong(value), format);
        if(skuProperty != null && isCode(format) && !skuProperty.equals("")){
            Map itemInner = new HashMap();
            itemInner.put("codeValue",skuProperty);
            return codeParse.parseValue(itemInner,"codeValue",convertMap.get(format));
        }
        return  skuProperty==null?"":skuProperty;
    }

    private Object getSkuProperty(long skuId,String format){
        Optional<TWmsSkuEntity> optional= skuList.stream().filter(x->x.getId()==skuId).findFirst();
        Object value = "";

        if(optional.isPresent()){
            TWmsSkuEntity skuEntity = optional.get();
            String actField = convertMap.get(format);
            switch (actField){
                case "itemName" : value=skuEntity.getItemName();break;
                case "barcode" : value=skuEntity.getBarcode();break;
                case "description" : value=skuEntity.getDescription();break;
                case "sku" : value=skuEntity.getSku();break;
                default:value="";
            }

        }

        return  value;
    }

    @Override
    public void loadParseData() {
        if (!sourceDataList.isEmpty()){
            this.skuList = skuService.findSkuLists (listToString(sourceDataList.stream().collect(Collectors.toList()),CharPool.COMMA) , dbShardVO);
        }
    }

    @Override
    public boolean isMatch(String formatKey) {
        return convertMap.containsKey(formatKey);
    }

    public void setDbShardVO(DbShardVO dbShardVO) {
        this.dbShardVO = dbShardVO;
    }


    @Override
    public void setSourceData(Map sourceData,String field,String formatKey) {
        if(isCode(formatKey)){
            codeParse.setSourceData(null,"",convertMap.get(formatKey));
        }

        long skuId = MapUtils.getLongValue(sourceData,"skuId");
        if(skuId != 0)
            sourceDataList.add(skuId);
    }

    @Override
    public boolean isNeedData(String formatKey) {
        return true;
    }

    private boolean isCode(String formatKey){
        String value = convertMap.get(formatKey);
        if(Objects.nonNull(value) && value.startsWith("code")){
            return true;
        }
        return false;
    }

    public void setCodeParse(CodeParse codeParse) {
        this.codeParse = codeParse;
    }

    public ISkuService getSkuService() {
        return skuService;
    }

    public void setSkuService(ISkuService skuService) {
        this.skuService = skuService;
    }

    private String listToString(List list, char separator) {
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }
}
