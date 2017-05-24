package com.huamengtong.wms.api.controller.common;

import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.formwork.db.splitdb.ShareDbUtil;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.enums.ResponseEnum;
import com.huamengtong.wms.respoes.ApiResponseResult;

public class ApiBaseController {


    protected DbShardVO getDefaultDbShardVO(DbShareField...source) {
        CurrentUserEntity currentUserEntity = new CurrentUserEntity();
        currentUserEntity.setTenantId(GlobalConstants.DEFAULT_TENANT_ID);
        currentUserEntity.setUserName(GlobalConstants.GLOBAL_USER_NAME);
        return ShareDbUtil.getDbShardVO(currentUserEntity,GlobalConstants.DEFAULT_WAREHOUST_ID,source);
    }

    /**
     * 分库分表标识
     * @return
     */
    protected DbShardVO getDbShardVO(Long warehouseId, DbShareField...source) {
        CurrentUserEntity currentUserEntity = new CurrentUserEntity();
        currentUserEntity.setTenantId(GlobalConstants.DEFAULT_TENANT_ID);
        currentUserEntity.setUserName(GlobalConstants.GLOBAL_USER_NAME);
        return ShareDbUtil.getDbShardVO(currentUserEntity,warehouseId,source);
    }


    public ApiResponseResult getApiSucMessage(){
        ApiResponseResult responseResult = new ApiResponseResult();
        responseResult.setSuccess(Boolean.TRUE);
        responseResult.setCode(ResponseEnum.SUCCESS.getCode());
        return responseResult;
    }

    public ApiResponseResult getApiSucMessage(Object data){
        ApiResponseResult responseResult = new ApiResponseResult();
        responseResult.setSuccess(Boolean.TRUE);
        responseResult.setCode(ResponseEnum.SUCCESS.getCode());
        responseResult.setData(data);
        return responseResult;
    }

    public ApiResponseResult getApiErrMessage(){
        ApiResponseResult responseResult = new ApiResponseResult();
        responseResult.setSuccess(Boolean.FALSE);
        responseResult.setCode(ResponseEnum.FAIL.getCode());
        return responseResult;
    }

    public ApiResponseResult getApiErrMessage(String message){
        ApiResponseResult responseResult = new ApiResponseResult();
        responseResult.setSuccess(Boolean.FALSE);
        responseResult.setCode(ResponseEnum.FAIL.getCode());
        responseResult.setMessage(message);
        return responseResult;
    }
}
