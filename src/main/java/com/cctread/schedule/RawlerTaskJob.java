package com.cctread.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 任务定时器
 *
 * @author caiteng
 * @version 1.0 创建时间：2018-02-07 13:23
 */
@Component
public class RawlerTaskJob {

    /**
     * 每日凌晨1点执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void test() {
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
