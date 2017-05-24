package com.huamengtong.wms.core.annotation;

import com.huamengtong.wms.core.formwork.db.util.DbShareField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShardTable {

    String splitTable() default "";

    DbShareField dbShareField() default DbShareField.DEFAULT;

}
