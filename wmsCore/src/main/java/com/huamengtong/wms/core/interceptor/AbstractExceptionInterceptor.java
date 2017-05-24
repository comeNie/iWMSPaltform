package com.huamengtong.wms.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

public abstract interface AbstractExceptionInterceptor {
    Object intercept(ProceedingJoinPoint paramProceedingJoinPoint) throws Throwable;
}
