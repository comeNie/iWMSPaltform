package com.huamengtong.wms.sku;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * SkuServer 服务开启
 */
public class SkuServerDubboProviderMain {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:sku"+ File.separator + "spring-core.xml");
        context.start();
        System.out.println("SkuMainServer start success . spring  profile active ------ " + System.getProperty("spring.profiles.active"));
        new CountDownLatch(1).await();
    }
}  