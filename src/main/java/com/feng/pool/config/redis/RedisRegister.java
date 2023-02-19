package com.feng.pool.config.redis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
@Component
public class RedisRegister implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {
    @Autowired
    private SaasRedisProperty redisDataSourceProperties;

    /**
     * StringRedisTemplate的beanName后缀
     */
    public static final String TEMPLATE_NAME = "StringRedisTemplate";


   private static Map<String, RedisTemplate> REDIS_TEMPLATE_MAP= new HashMap<>();

    public RedisTemplate getRedisTemplate(String redis) {
        return REDIS_TEMPLATE_MAP.get(redis);
    }

    /**
     * 读取redis配置并创建对应的StringRedisTemplate
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        Map<String, RedisProperties> redisDataSource = redisDataSourceProperties.getSource();
        if (!CollectionUtils.isEmpty(redisDataSource)) {
            redisDataSource.forEach((name, dataSource) -> {
                StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
                RedisConnectionFactory redisConnection = getRedisConnection(dataSource);
                stringRedisTemplate.setConnectionFactory(redisConnection);
                stringRedisTemplate.afterPropertiesSet();
                // 向ioc容器中注入StringRedisTemplate
                String templateName = name + TEMPLATE_NAME;
                System.out.println("RedisName="+templateName);
                listableBeanFactory.registerSingleton(templateName, stringRedisTemplate);
                REDIS_TEMPLATE_MAP.put(name,stringRedisTemplate);
            });
        }
    }

    /**
     * 配置redisConnection
     *
     * @param dataSource
     */
    private RedisConnectionFactory getRedisConnection(RedisProperties dataSource) {
        return getLettuceConnectionFactory(dataSource);
    }

    /**
     * 获取LettuceConnection
     *
     * @param dataSource
     */
    private RedisConnectionFactory getLettuceConnectionFactory(RedisProperties dataSource) {
        RedisStandaloneConfiguration redisConfig = getRedisConfig(dataSource);

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfig);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }



    /**
     * 获取redis连接配置
     *
     * @param dataSource
     */
    private RedisStandaloneConfiguration getRedisConfig(RedisProperties dataSource) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(dataSource.getHost());
        redisConfig.setPort(dataSource.getPort());
        redisConfig.setPassword(dataSource.getPassword());
        redisConfig.setDatabase(dataSource.getDatabase());
        return redisConfig;
    }


}
