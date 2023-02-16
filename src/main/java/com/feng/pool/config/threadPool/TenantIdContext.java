package com.feng.pool.config.threadPool;

import com.feng.pool.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @author feng
 * @date 2023/2/16
 */
public class TenantIdContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(TenantIdContext.class);

    /**
     * 租户容器
     */
    private static final ThreadLocal<String> TENANT_CONTEXT = new InheritableThreadLocal<>();


    public static String createTenantId() {
        String s = TENANT_CONTEXT.get();
        if(StringUtils.isEmpty(s)){
            return "pool-a";
        }
        return s;

    }

    public static void setTenantIdIfAbsent() {
        if (TENANT_CONTEXT.get()== null) {
            TENANT_CONTEXT.set(createTenantId());
        }
    }

    public static String getTenantId() {
        return TENANT_CONTEXT.get();
    }



    public static void setTenantId(String traceId) {
        TENANT_CONTEXT.set(traceId);
    }

    public static void clear() {
        TENANT_CONTEXT.remove();
    }

    public static <T> Callable<T> wrap(final Callable<T> callable) {
        return () -> {
            createTenantId();
            try {
                return callable.call();
            } finally {
                clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable) {
        return () -> {
            createTenantId();
            try {
                runnable.run();
            } finally {
                clear();
            }
        };
    }
}
