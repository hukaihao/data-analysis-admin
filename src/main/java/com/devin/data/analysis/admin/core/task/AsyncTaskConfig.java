package com.devin.data.analysis.admin.core.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

@Configuration
@EnableAsync
public class AsyncTaskConfig extends TaskExecutorComponent implements AsyncConfigurer {
    @Bean
    public AsyncTaskExecutor threadPoolTaskExecutor() {
        return this;
    }

    @PostConstruct
    public void initThreadPoolTaskExecutor() {
        super.initThreadPoolTaskExecutor();
        this.setThreadNamePrefix("AsyncTask-");
    }


}
