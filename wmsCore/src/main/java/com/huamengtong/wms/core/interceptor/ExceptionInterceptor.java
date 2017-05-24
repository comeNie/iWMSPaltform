package com.huamengtong.wms.core.interceptor;

import com.huamengtong.wms.aspectj.handle.HandleRequest;
import com.huamengtong.wms.aspectj.handle.HandleResponse;
import com.huamengtong.wms.core.execption.handle.ExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Date;

/***
 * 统一异常处理拦截器，通过spring aop 方式切入
 */
public class ExceptionInterceptor implements AbstractExceptionInterceptor {

    private ExceptionHandler exceptionHandler;

    @Override
    public Object intercept(ProceedingJoinPoint paramProceedingJoinPoint) throws Throwable {
        HandleRequest request = buildHandleRequest(paramProceedingJoinPoint);
        Object re = null;
        try {
            re = paramProceedingJoinPoint.proceed();
            request.setResult(re);
        } catch (Exception e) {
            e.printStackTrace();
            request.setException(e);
            HandleResponse response = this.exceptionHandler.handle(request);
            re = response.getResult();
        }
        return re;
    }

    private HandleRequest buildHandleRequest(ProceedingJoinPoint pjp){
        long startTime = new Date().getTime();
        HandleRequest request = new HandleRequest();
        request.setStartTime(Long.valueOf(startTime));
        request.setProceedingJoinPoint(pjp);
        return request;
    }

    public ExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
}