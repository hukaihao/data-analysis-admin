package com.devin.data.analysis.admin.product.biz.impl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import static org.junit.Assert.*;

public class AsyncConfigTestTest {
    private Logger logger = LoggerFactory.getLogger(CQSSCQueryScheduleTask.class);

    private int testAsyn = 1;

    @Autowired
    private AsyncConfigTest asyncConfigTest;

    @Test
    @Scheduled(initialDelay = 1000, fixedRate = 6000)   //initialDelay = 1000表示延迟1000ms执行第一次任务
    public void testAsyn() throws Exception{
        logger.info("===testAsyn: 第{}次执行方法", testAsyn++);
        asyncConfigTest.doTask1();
    }

}