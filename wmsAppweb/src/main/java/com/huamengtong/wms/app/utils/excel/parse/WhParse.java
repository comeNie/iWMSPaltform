package com.huamengtong.wms.app.utils.excel.parse;

import com.huamengtong.wms.entity.main.TWmsWarehouseEntity;
import com.huamengtong.wms.main.service.IWarehouseService;
import com.huamengtong.wms.parse.BasicParse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class WhParse extends BasicParse {
    private IWarehouseService warehouseService;
    private Set<Long> sourceDataList = new HashSet();
    private List<TWmsWarehouseEntity> dataList = new ArrayList();

    @Override
    public Object parseValue(Map item,String field, String format) {
        String value = MapUtils.getString(item,field);
        if(StringUtils.isNotEmpty(value)){
            Optional<TWmsWarehouseEntity> optional = dataList.stream().filter(x->x.getId() == Long.parseLong(value)).findFirst();
            if(optional.isPresent()){
                return optional.get().getWarehouseName();
            }
        }
        return "";
    }

    @Override
    public void loadParseData() {
        if(!sourceDataList.isEmpty()){
            dataList = warehouseService.searchWarehouseByIds(sourceDataList.stream().collect(Collectors.toList()));
        }
    }

    @Override
    public boolean isMatch(String formatKey) {
        return formatKey.equals("wh");
    }

    @Override
    public void setSourceData(Map sourceDataMap, String field,String formatKey) {
        long whId = MapUtils.getLongValue(sourceDataMap,field);
        if(whId != 0)
            sourceDataList.add(whId);
    }

    @Override
    public boolean isNeedData(String formatKey) {
        return true;
    }

    public void setWarehouseService(IWarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }
}
