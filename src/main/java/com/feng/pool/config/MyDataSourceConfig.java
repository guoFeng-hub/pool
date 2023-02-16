package com.feng.pool.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.feng.pool.filter.TraceFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class MyDataSourceConfig {

    @Bean
    public RoutingDataSource routingDataSource(DateSourcePropertyMap dataSource) {


        Map<Object, Object> map = new HashMap<>();
        dataSource.getConfig().forEach((k,v)->{
            map.put(k,v);
        });

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(map);
        routingDataSource.setDefaultTargetDataSource(map.get("pool-a"));
        return routingDataSource;
    }

    /**
     * 设置会话工厂
     */
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(RoutingDataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //配置数据源为多数据源
        factoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resource = new PathMatchingResourcePatternResolver();

        factoryBean.setMapperLocations(resource.getResources("classpath:mapper/*.xml"));
           return factoryBean.getObject();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean traceFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TraceFilter());
        registration.addUrlPatterns("/*");
        registration.setName("traceFilter");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }
}
