package com.devin.data.analysis.admin.core.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hukaihao
 * @create 2018-07-04
 * 处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程 maximumPoolSize
 */
@Component
public class CommonExecutorComponent extends ThreadPoolTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CommonExecutorComponent.class);

    private static final long serialVersionUID = 4972670441575715193L;

    @PostConstruct
    public void initThreadPoolTaskExecutor() {
        super.setThreadNamePrefix("CommonExecutor-");
        //核心线程数，如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务
        super.setCorePoolSize(4);
        //如果一个线程处在空闲状态的时间超过了该属性值，就会因为超时而退出。举个例子，如果线程池的核心大小corePoolSize=5，而当前大小poolSize =8，那么超出核心大小的线程，会按照keepAliveTime的值判断是否会超时退出
        super.setKeepAliveSeconds(200);
        //线程池中允许的最大线程数，线程池中的当前线程数目不会超过该值。如果队列中任务已满，并且当前线程个数小于maximumPoolSize，那么会创建新的线程来执行任务。
        super.setMaxPoolSize(16);
        //等待运行的线程阻塞队列的长度，务必设置。否则默认值是Integer.MAX，如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
        super.setQueueCapacity(30);
        //超出阻塞队列时，新的线程处理方式。下面的处理方式是：在主线程运行
        super.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //  重要，优雅关闭
        super.setWaitForTasksToCompleteOnShutdown(true);
    }


    private void showThreadPoolInfo(String prefix) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        if (null == threadPoolExecutor) {
            return;
        }

        logger.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(),
                prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }

}
