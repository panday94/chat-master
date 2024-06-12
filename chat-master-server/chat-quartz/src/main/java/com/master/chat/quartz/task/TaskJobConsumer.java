package com.master.chat.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: panday
 * https://www.panday94.xyz
 * Copyright Ⓒ 2023 曜栋网络科技工作室 Limited All rights reserved.
 */
@Slf4j
@EnableScheduling
@Component
public class TaskJobConsumer {

    //@Scheduled(cron = "0 */1 * * * ?")
   /* public void activityStatus(){
        System.out.println("13213");
    }*/



}
