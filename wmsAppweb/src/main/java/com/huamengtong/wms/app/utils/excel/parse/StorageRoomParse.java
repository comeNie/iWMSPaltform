package com.huamengtong.wms.app.utils.excel.parse;

import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.parse.BasicParse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by StickT on 2016/12/16.
 */
public class StorageRoomParse extends BasicParse {

    private IStorageRoomService storageRoomService;
    private HashSet<Long> sourceDataList = new HashSet<>();
    private List<TWmsStorageRoomEntity> storageRoomEntityList = new ArrayList<>();


    @Override
    public Object parseValue(Map item, String field, String format) {
        String value = MapUtils.getString(item,field);
        if (StringUtils.isNotEmpty(value)){
            Optional<TWmsStorageRoomEntity> optional = storageRoomEntityList.stream().filter(x->x.getId().equals(Long.parseLong(value))).findFirst();
            if (optional.isPresent()){
                return optional.get().getRoomNo();
            }
        }
        return "";
    }

    @Override
    public void loadParseData() {
        if(!sourceDataList.isEmpty()){
            storageRoomEntityList = storageRoomService.getByIds(sourceDataList.stream().collect(Collectors.toList()));
        }
    }

    @Override
    public boolean isMatch(String formatKey) {
        return formatKey.equals("storageRoom");
    }

    @Override
    public void setSourceData(Map sourceDataMap, String field, String formatKey) {
        Long storageRoomId = MapUtils.getLong(sourceDataMap,"storageRoomId");
        if(storageRoomId!=null && storageRoomId!=0){
            sourceDataList.add(storageRoomId);
        }
    }

    @Override
    public boolean isNeedData(String formatKey) {
        return true;
    }

    public void setStorageRoomService(IStorageRoomService storageRoomService){
        this.storageRoomService = storageRoomService;
    }
}
