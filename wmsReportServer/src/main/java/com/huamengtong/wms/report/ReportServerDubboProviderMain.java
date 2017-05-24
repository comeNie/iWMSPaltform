package com.huamengtong.wms.report;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * ReportServer 服务开启
 */
public class ReportServerDubboProviderMain {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:report"+ File.separator + "spring-core.xml");
        context.start();
        System.out.println("wmsReportServer start success . spring  profile active ------ " + System.getProperty("spring.profiles.active"));
        new CountDownLatch(1).await();
    }
}  