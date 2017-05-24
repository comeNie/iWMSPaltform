package com.huamengtong.wms.parse;

import java.util.Map;

/**
 * Created by Edwin on 2016/12/9.
 * Basic Parse
 */
public abstract class BasicParse {

    protected boolean isLoad = false;
    public boolean isLoad(){
        return isLoad;
    }
    public abstract Object parseValue(Map item, String field, String format);
    public abstract void loadParseData();
    public abstract boolean isMatch(String formatKey);
    public abstract void setSourceData(Map sourceDataMap,String field,String formatKey);
    public abstract boolean isNeedData(String formatKey);
}
