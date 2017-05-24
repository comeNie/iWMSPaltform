package com.huamengtong.wms.app.utils.excel.parse;

import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.parse.BasicParse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by StickT on 2016/12/26.
 */
public class StorageRoomStringParse extends BasicParse {

    private IStorageRoomService storageRoomService;
    private HashSet<Long> sourceDataList = new HashSet<>();
    private List<TWmsStorageRoomEntity> storageRoomEntityList = new ArrayList<>();
    Map<Long,String> dataList = new HashMap<>();

    @Override
    public Object parseValue(Map item, String field, String format) {
        String value = MapUtils.getString(item,field);
        String[] ids = value.split(",");
        if (ArrayUtils.isEmpty(ids)) {
            return "";
        }
        return Arrays.stream(ids)
                .map(id -> MapUtils.getString(dataList,Long.parseLong(id)))
                .collect(Collectors.joining(","));
    }

    @Override
    public void loadParseData() {
        if(!sourceDataList.isEmpty()){
            this.storageRoomEntityList = storageRoomService.getByIds(sourceDataList.stream().collect(Collectors.toList()));
            for(TWmsStorageRoomEntity storageRoomEntity:storageRoomEntityList){
                this.dataList.put(storageRoomEntity.getId(),storageRoomEntity.getRoomNo());
            }
            isLoad = true;
        }
    }

    @Override
    public boolean isMatch(String formatKey) {
        return formatKey.equals("storageRoomString");
    }

    @Override
    public void setSourceData(Map sourceDataMap, String field, String formatKey) {
        String  ids = MapUtils.getString(sourceDataMap,field);
        String[] storageRoomIds = ids.split(",");
        for (String storageRoomId : storageRoomIds) {
            Long id = Long.parseLong(storageRoomId);
            sourceDataList.add(id);
        }
    }

    @Override
    public boolean isNeedData(String formatKey) {
        return true;
    }

    public void setStorageRoomService(IStorageRoomService storageRoomService) {
        this.storageRoomService = storageRoomService;
    }
}
