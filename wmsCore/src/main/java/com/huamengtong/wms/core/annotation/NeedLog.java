package com.huamengtong.wms.core.annotation;

import com.huamengtong.wms.em.LogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Edwin on 2016/11/4.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLog {

    String value() default "";

    LogType[]type() default {LogType.OPREATION};

}
