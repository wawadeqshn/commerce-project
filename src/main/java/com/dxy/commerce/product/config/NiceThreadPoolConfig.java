package com.dxy.commerce.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 功能说明: 线程池配置
 *
 * @author dingxy
 * @date 2021/2/27 4:04 下午
 */
@Configuration
public class NiceThreadPoolConfig {

    @Bean(name = "fengyanExecutor")
    public ThreadPoolTaskExecutor niceExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 线程池创建时候初始化的线程数
        executor.setCorePoolSize(10);
        // 线程池最大的线程数，只有在缓冲队列满了之后，才会申请超过核心线程数的线程
        executor.setMaxPoolSize(200);
        // 用来缓冲执行任务的队列
        executor.setQueueCapacity(100);
        // 当超过了核心线程之外的线程，在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀 可以用于定位处理任务所在的线程池
        executor.setThreadNamePrefix("fengyanExecutor-");
        // 该方法用来设置线程池关闭的时候 等待 所有任务都完成后，再继续销毁其他的 Bean，
        // 这样这些异步任务的销毁就会先于 数据库连接池对象的销毁。
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 该方法用来设置线程池中 任务的等待时间，如果超过这个时间还没有销毁就 强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 设置为守护线程
        executor.setDaemon(true);
        return executor;
    }
}
