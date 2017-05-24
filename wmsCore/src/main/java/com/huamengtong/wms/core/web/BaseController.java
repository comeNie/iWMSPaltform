package com.huamengtong.wms.core.web;

import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.formwork.db.splitdb.ShareDbUtil;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.formwork.db.vo.DbShardVO;
import com.huamengtong.wms.core.web.message.Messages;
import com.huamengtong.wms.enums.ResultTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    protected Messages messages;

    protected HttpSession getSession() {
        return request.getSession();
    }

    protected HttpServletRequest getRequest() {
        return this.request;
    }

    protected HttpServletResponse getResponse() {
        return this.response;
    }

    protected CurrentUserEntity getSessionCurrentUser() {
       return (CurrentUserEntity)getSession().getAttribute(GlobalConstants.SESSION_KEY);
    }

    protected void setCurrentWarehouseId(long warehouseId) {
        getSession().setAttribute(GlobalConstants.WAREHOUSE_ID, warehouseId);
    }

    protected long getCurrentWarehouseId() {
       Object warehouseId = getSession().getAttribute(GlobalConstants.WAREHOUSE_ID);
        if(warehouseId != null){
            return Long.parseLong(warehouseId.toString());
        }
       return 0;
    }

    protected void setCurrentTenantId(Long tenantId) {
        getSession().setAttribute(GlobalConstants.TENANT_ID, tenantId);
    }

    protected long getCurrentTenantId() {
        Object tenantId = getSession().getAttribute(GlobalConstants.TENANT_ID);
        if(tenantId != null){
            return Long.parseLong(tenantId.toString());
        }
        return 0;
    }



    protected ResponseResult getMessage(String messageKey,Object ...params) {
        ResponseResult responseResult = new ResponseResult(messages);
        if(messageKey.startsWith("S")){
            return getSucMessage(messageKey,params);
        }else if(messageKey.startsWith("E")){
            return getFaultMessage(messageKey,params);
        }
        return responseResult;
    }

    protected ResponseResult getMessage(MessageResult mr) {
        String messageKey = mr.getCode();
        ResponseResult responseResult = new ResponseResult(messages);
        if(messageKey.startsWith("S")){
            responseResult = getSucMessage(messageKey,mr.getParams());
            responseResult.setResult(mr.getResult());
            return responseResult;
        }else if(messageKey.startsWith("E")){
            return getFaultMessage(messageKey,mr.getParams());
        }
        return responseResult;
    }
    protected ResponseResult getSucMessage() {
        ResponseResult responseResult = new ResponseResult(messages);
        responseResult.setSucMessage();
        return responseResult;
    }

    protected ResponseResult getSucMessage(String messageKey,Object ...params) {
        ResponseResult responseResult = new ResponseResult(messages);
        responseResult.setSucMessage(messageKey, ResultTypeEnum.POPUP,params);
        return responseResult;
    }

    protected ResponseResult getSucMessage(String messageKey,ResultTypeEnum resultType,Object ...params) {
        ResponseResult responseResult = new ResponseResult(messages);
        responseResult.setSucMessage(messageKey,resultType,params);
        return responseResult;
    }
    protected ResponseResult getFaultMessage() {
        ResponseResult responseResult = new ResponseResult(messages);
        responseResult.setFaultMessage("");
        return responseResult;
    }
    protected ResponseResult getFaultMessage(String messageKey,Object ...params) {
        ResponseResult responseResult = new ResponseResult(messages);
        responseResult.setFaultMessage(messageKey,params);
        return responseResult;
    }

    protected ResponseResult getFaultMessage(String messageKey,ResultTypeEnum resultType,Object ...params) {
        ResponseResult responseResult = new ResponseResult(messages);
        responseResult.setFaultMessage(messageKey,resultType,params);
        return responseResult;
    }

    protected ResponseResult getFaultResultData(Object obj){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setFaultResult(obj);
        return responseResult;
    }
    protected ResponseResult getSucResultData(Object obj){
        ResponseResult responseResult = new ResponseResult();
        responseResult.setResult(obj);
        return responseResult;
    }

    protected ResponseResult chooseMessageByKey(String messageKey) {
        if (StringUtils.isBlank(messageKey)) {
            return getSucMessage();
        } else {
            return getMessage(messageKey);
        }
    }

    /**
     * 主分库分表 根据用户的租户id分库
     * @return
     */
    protected DbShardVO getDbShardVO(DbShareField...source) {
        return ShareDbUtil.getDbShardVO(this.getSessionCurrentUser(),this.getCurrentWarehouseId(),source);
    }

    protected Integer getOffset(Integer page,Integer pageSize) {
        if(pageSize<=0){
            pageSize = 1;
        }
        return (page-1)*pageSize;
    }

    /**
     * 退出登录
     */
    public void removeSessionAttribute(){
        getSession().removeAttribute(GlobalConstants.SESSION_KEY);
        getSession().removeAttribute(GlobalConstants.WAREHOUSE_ID);
        getSession().removeAttribute(GlobalConstants.TENANT_ID);
    }

    public String getBasePath(){
        return getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath() + "/";
    }

  }
