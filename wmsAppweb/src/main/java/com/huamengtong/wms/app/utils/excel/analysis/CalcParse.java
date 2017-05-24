package com.huamengtong.wms.app.utils.excel.analysis;

import com.huamengtong.wms.parse.BasicParse;
import com.huamengtong.wms.utils.CharPool;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Edwin on 2016/12/9.
 */
public class CalcParse extends BasicParse {

    public final Map<String,Set<String>> formulaFieldMap = new ConcurrentHashMap<>();

    @Override
    public Object parseValue(Map item, String field, String format) {
        String formula = getFormula(format);
        Set<String> fieldSet = getFieldSet(formula);
        return calcExec(item,formula,fieldSet);
    }

    @Override
    public void loadParseData() {

    }

    @Override
    public boolean isMatch(String formatKey) {
        return formatKey.contains("calcParse");
    }

    @Override
    public void setSourceData(Map sourceDataMap, String field, String formatKey) {

    }

    @Override
    public boolean isNeedData(String formatKey) {
        return false;
    }

    private Object calcExec(Map item,String formula,Set<String> fieldSet){
        Object result = 0.0;

        Iterator<String> it = fieldSet.iterator();

        while(it.hasNext()){
            String field = it.next();
            Object fieldObj = item.get(field);
            if(fieldObj != null && !fieldObj.toString().equals("")){
                formula = formula.replaceAll(field,fieldObj.toString());
            }else{
                formula = formula.replaceAll(field,"0");
            }
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");

        try {
            result = engine.eval(formula);

        } catch (ScriptException ex) {
            return "0";
        }
        return result;
    }

    private  Set<String> getFieldSet(String formula){
        if(formulaFieldMap.containsKey(formula)){
            return formulaFieldMap.get(formula);
        }
        Set<String> fieldSet = new HashSet<String>();
        String feilds = formula.replaceAll("[\\-,\\+,/,\\*,(,)]"," ");
        String fieldArr[] = feilds.split(" ");

        for(int i = 0;i < fieldArr.length;i++){
            if(StringUtils.isNotBlank(fieldArr[i])){
                fieldSet.add(fieldArr[i]);
            }
        }

        formulaFieldMap.put(formula,fieldSet);

        return fieldSet;
    }

    private String getFormula(String parseFormula){
        return parseFormula.substring(parseFormula.indexOf(CharPool.COLON)+ 1);
    }

}
