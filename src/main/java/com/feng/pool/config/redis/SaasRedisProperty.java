package com.feng.pool.config.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = SaasRedisProperty.PREFIX)
public class SaasRedisProperty {
    public static final String PREFIX = "spring.redis";

    private Map<String, RedisProperties> source;

    public Map<String, RedisProperties> getSource() {
        return source;
    }

    public void setSource(Map<String, RedisProperties> source) {
        this.source = source;
    }
}
