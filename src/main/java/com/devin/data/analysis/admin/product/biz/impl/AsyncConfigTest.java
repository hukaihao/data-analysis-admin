package com.devin.data.analysis.admin.product.biz.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
@Component
public class AsyncConfigTest {
    private static final Logger logger = LoggerFactory.getLogger(AsyncConfigTest.class);

    @Async
    public Future<String> doTask1() throws InterruptedException{
        logger.info("Task1 started.");
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        long end = System.currentTimeMillis();
        logger.info("Task1 finished, time elapsed: {} ms.", end-start);
        return new AsyncResult<>("Task1 accomplished!");
    }

}
