package com.feng.pool.config.threadPool;

import com.feng.pool.config.Constants;
import org.slf4j.MDC;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * @author feng
 * @date 2023/2/9
 */
public class ThreadMdcUtil {
    public static String createTraceId() {

        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static void setTraceIdIfAbsent() {
        if (MDC.get(Constants.TRACE_ID) == null) {
            MDC.put(Constants.TRACE_ID, createTraceId());
        }
    }

    public static String getTraceId() {
        return MDC.get(Constants.TRACE_ID);
    }

    public static void setTraceId() {
        MDC.put(Constants.TRACE_ID, createTraceId());
    }

    public static void setTraceId(String traceId) {
        MDC.put(Constants.TRACE_ID, traceId);
    }

    public static void clear() {
        MDC.clear();
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
