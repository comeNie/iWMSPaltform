package com.huamengtong.wms.respoes;

import com.huamengtong.wms.enums.ResponseEnum;

public class ResponseUtil {

    public static <T> Response<T> getResponse(T data){
        if (data instanceof Boolean) {
            Boolean dataTemp = (Boolean)data;
            if (dataTemp.booleanValue())
                return new Response(ResponseEnum.SUCCESS.getCode(), data);
            return new Response(ResponseEnum.FAIL.getCode(), ResponseEnum.FAIL.getDescription());
        }
        return new Response(ResponseEnum.SUCCESS.getCode(), data);
    }
}
