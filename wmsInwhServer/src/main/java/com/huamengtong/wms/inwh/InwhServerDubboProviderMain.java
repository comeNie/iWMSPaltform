package com.huamengtong.wms.inwh;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * MainServer 服务开启
 */
public class InwhServerDubboProviderMain {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:inwh"+ File.separator + "spring-core.xml");
        context.start();
        System.out.println("wmsInwhServer start success . spring  profile active ------ " + System.getProperty("spring.profiles.active"));
        new CountDownLatch(1).await();
    }
}  