package com.master.chat.quartz.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Component("demoTask")
public class DemoTask {

    public void testMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(String.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void testParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void testNoParams() {
        System.out.println("执行无参方法");
    }

}
