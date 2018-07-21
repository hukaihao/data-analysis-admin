package com.devin.data.analysis.admin.core.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.PostConstruct;

/**
 * @author hukaihao
 * 所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
 */
@Configuration
public class ScheduleTaskConfig extends TaskExecutorComponent  implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度10的定时任务线程池
        taskRegistrar.setScheduler(this);
    }

    @PostConstruct
    public void initThreadPoolTaskExecutor() {
        super.initThreadPoolTaskExecutor();
        this.setThreadNamePrefix("ScheduleTask-");
    }

}

