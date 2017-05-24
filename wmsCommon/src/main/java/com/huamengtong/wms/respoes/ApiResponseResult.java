package com.huamengtong.wms.respoes;


public class ApiResponseResult extends Response {

    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ApiResponseResult(){

    }

    public ApiResponseResult(Integer code, Object data, String message, Boolean success) {
        super(code, data, message);
        this.success = success;
    }

    public ApiResponseResult(Object data, Boolean success) {
        super(data);
        this.success = success;
    }

    public ApiResponseResult(Integer code, Object data, Boolean success) {
        super(code, data);
        this.success = success;
    }

    public ApiResponseResult(Integer code, String message, Boolean success) {
        super(code, message);
        this.success = success;
    }
}
