package com.cctread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * spring boot 项目起始类
 *
 * @author cait
 */
@ComponentScan
@SpringBootApplication
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
