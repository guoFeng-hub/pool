package com.feng.pool.config.threadPool;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author feng
 * @date 2023/2/9
 */
public class ThreadPoolMdcWrapper extends ThreadPoolTaskExecutor {
    @Override
    public void execute(Runnable task) {
        Runnable wrap = TenantIdContext.wrap(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        super.execute(wrap );
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        Runnable wrap = TenantIdContext.wrap(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
        super.execute(ThreadMdcUtil.wrap(wrap, MDC.getCopyOfContextMap()), startTimeout);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Callable<T> wrap = TenantIdContext.wrap(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));

        return super.submit(wrap);
    }

    @Override
    public Future<?> submit(Runnable task) {
        Runnable wrap = TenantIdContext.wrap(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));

        return super.submit(wrap);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        Runnable wrap = TenantIdContext.wrap(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));

        return super.submitListenable(wrap);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        Callable<T> wrap = TenantIdContext.wrap(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));

        return super.submitListenable(wrap);
    }

}
