package com.master.chat.quartz.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author: 周杰
 * @date: 2023/01/31
 * @version: master
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Log4j2
@EnableScheduling
@Component
public class TaskJobConsumer {

    //@Scheduled(cron = "0 */1 * * * ?")
   /* public void activityStatus(){
        System.out.println("13213");
    }*/



}
