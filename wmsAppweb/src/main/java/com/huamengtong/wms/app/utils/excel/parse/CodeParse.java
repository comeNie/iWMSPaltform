package com.huamengtong.wms.app.utils.excel.parse;

import com.huamengtong.wms.main.service.ICodeService;
import com.huamengtong.wms.parse.BasicParse;
import org.apache.commons.collections.MapUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CodeParse extends BasicParse {

    private ICodeService codeService;
    private List<Map<String, String>> codeList;
    private Set<String> sourceDataList = new HashSet();

    @Override
    public Object parseValue(Map item,String field, String format) {
        String value = MapUtils.getString(item,field);

        format = format.split("\\:")[1];
        String formatStr = format;

        if(codeList != null){
            Optional<Map<String, String>> optional = codeList.stream().
                    filter(t -> formatStr.equals(MapUtils.getString(t, "listName"))).
                    filter(t -> value.equals(MapUtils.getString(t, "codeValue"))).
                    findFirst();

            if (optional.isPresent()) {
                return MapUtils.getString(optional.get(), "codeName");
            }
        }
        return "";
    }

    @Override
    public void loadParseData() {
        if(!sourceDataList.isEmpty() && !isLoad){
            codeList = codeService.getCodeList(sourceDataList.stream().collect(Collectors.toList()));
            isLoad = true;
        }
    }

    @Override
    public boolean isMatch(String formatKey) {
        try {
            return formatKey.startsWith("code");
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    @Override
    public void setSourceData(Map sourceData,String field,String formatKey) {
        Objects.requireNonNull(formatKey);
        String codeType = formatKey.split("\\:")[1];
        sourceDataList.add(codeType);
    }

    @Override
    public boolean isNeedData(String formatKey) {
        return true;
    }


    public void setCodeService(ICodeService codeService) {
        this.codeService = codeService;
    }
}
