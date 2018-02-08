package com.cctread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * spring boot 项目起始类
 *
 * @author cait
 * @EnableTransactionManagement 开启数据库事务注解
 */
@ComponentScan
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class CctreadRawlerApplication {
    /**
     * 项目起始方法
     *
     * @param args 起始参数
     */
    public static void main(String[] args) {
        SpringApplication.run(CctreadRawlerApplication.class, args);
    }
}
