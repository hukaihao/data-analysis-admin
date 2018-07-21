package com.devin.data.analysis.admin.core.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hukaihao
 * @create 2018-07-05
 */
public class TaskExecutorComponent extends ThreadPoolTaskScheduler {

    private static final long serialVersionUID = 4972670441575715193L;

    @PostConstruct
    public void initThreadPoolTaskExecutor() {
        super.setThreadNamePrefix("CommonScheduler-");
        super.setPoolSize(4);
        //该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        super.setAwaitTerminationSeconds(60);
        //超出阻塞队列时，新的线程处理方式。下面的处理方式是：在主线程运行
        super.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //  重要，优雅关闭
        super.setWaitForTasksToCompleteOnShutdown(true);
    }
}
