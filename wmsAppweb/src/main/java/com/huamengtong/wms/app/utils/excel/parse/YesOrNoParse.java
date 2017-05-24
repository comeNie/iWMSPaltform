package com.huamengtong.wms.app.utils.excel.parse;

import com.huamengtong.wms.parse.BasicParse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class YesOrNoParse extends BasicParse {

    Map map = new HashMap();

    @Override
    public Object parseValue(Map item,String field, String format) {
        String value = MapUtils.getString(item,field);
        if(StringUtils.isBlank(value)){
            return "";
        }
        String key = MapUtils.getString(map,format);
        if(StringUtils.isBlank(key)){
            if("1".equals(value)){
                return "是";
            }else{
                return "否";
            }
        }else{
            if("1".equals(value)){
                return "已" + key;
            }else{
                return "未" + key;
            }
        }
    }

    @Override
    public void loadParseData() {
        map.put("yesOrNoHold","冻结");
        map.put("yesOrNoAuth","授权");
    }

    @Override
    public boolean isMatch(String formatKey) {
        return formatKey.startsWith("yesOrNo");
    }

    @Override
    public void setSourceData(Map sourceDataMap, String field, String formatKey) {

    }
    @Override
    public boolean isNeedData(String formatKey) {
        return false;
    }
}
