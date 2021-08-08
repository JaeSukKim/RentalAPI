package com.rental.api.configuration.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${spring.task.execution.pool.core-size}")
    int poolCoreSize;

    @Value("${spring.task.execution.pool.max-size}")
    int poolMaxSize;

    @Value("${spring.task.execution.pool.queue-capacity}")
    int queueSize;

    @Bean
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(poolCoreSize);
        taskExecutor.setMaxPoolSize(poolMaxSize);
        taskExecutor.setQueueCapacity(queueSize);
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return taskExecutor;
    }
}
