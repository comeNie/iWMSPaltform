package com.huamengtong.wms.app.utils.excel.parse;

import com.huamengtong.wms.parse.BasicParse;
import com.huamengtong.wms.utils.DateUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class DateParse extends BasicParse {

    @Override
    public Object parseValue(Map item,String field, String format) {
        String value = MapUtils.getString(item, field);
        if(StringUtils.isBlank(value)){
            return "";
        }
        return DateUtil.timestampToString(Long.parseLong(value));
    }

    @Override
    public void loadParseData() {

    }

    @Override
    public boolean isMatch(String formatKey) {
        return formatKey.startsWith("date");
    }

    @Override
    public void setSourceData(Map sourceDataMap,String field, String formatKey) {

    }
    @Override
    public boolean isNeedData(String formatKey) {
        return false;
    }
}
