package com.huamengtong.wms.app.utils.excel.parse;

import com.huamengtong.wms.entity.main.TWmsCargoOwnerEntity;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.huamengtong.wms.parse.BasicParse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by StickT on 2016/12/16.
 */
public class CargoOwnerParse extends BasicParse {

    private ICargoOwnerService cargoOwnerService;
    private Set<Long> sourceDataList= new HashSet<>();
    private List<TWmsCargoOwnerEntity> cargoOwnerEntityList = new ArrayList<>();



    @Override
    public Object parseValue(Map item, String field, String format) {
        String value = MapUtils.getString(item,field);
        if(StringUtils.isNotEmpty(value)){
            Optional<TWmsCargoOwnerEntity> optional = cargoOwnerEntityList.stream().filter(x->x.getId() == Long.parseLong(value)).findFirst();
            if(optional.isPresent()){
                return optional.get().getFullName();
            }
        }
        return "";
    }

    @Override
    public void loadParseData() {
        if(!sourceDataList.isEmpty()){
            cargoOwnerEntityList = cargoOwnerService.findByIds(sourceDataList.stream().collect(Collectors.toList()));
        }

    }

    @Override
    public boolean isMatch(String formatKey) {
        return formatKey.equals("cargoOwner");
    }

    @Override
    public void setSourceData(Map sourceDataMap, String field, String formatKey) {
        Long cargoOwnerId = MapUtils.getLong(sourceDataMap,field);
        if(cargoOwnerId!=null && cargoOwnerId !=0){
            sourceDataList.add(cargoOwnerId);
        }

    }

    @Override
    public boolean isNeedData(String formatKey) {
        return true;
    }

    public void setCargoOwnerService(ICargoOwnerService cargoOwnerService){this.cargoOwnerService=cargoOwnerService;}
}
