package com.master.chat.quartz.constant;

/**
 * 任务调度通用常量
 *
 * @author: Yang
 * @date: 2023/01/31
 * @version: 1.0.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
public class ScheduleConstants {

    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /**
     * 执行目标key
     */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /**
     * 默认
     */
    public static final Integer MISFIRE_DEFAULT = 0;

    /**
     * 立即触发执行
     */
    public static final Integer MISFIRE_IGNORE_MISFIRES = 1;

    /**
     * 触发一次执行
     */
    public static final Integer MISFIRE_FIRE_AND_PROCEED = 2;

    /**
     * 不触发立即执行
     */
    public static final Integer MISFIRE_DO_NOTHING = 3;

}
