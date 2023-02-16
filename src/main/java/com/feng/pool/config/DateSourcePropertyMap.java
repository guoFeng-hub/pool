package com.feng.pool.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring.emily.datasource")
@Configuration
public class DateSourcePropertyMap {

    Map<String, DruidDataSource> config ;

    public Map<String, DruidDataSource> getConfig() {
        return config;
    }

    public void setConfig(Map<String, DruidDataSource> config) {
        this.config = config;
    }

    static class  DateSourceProperty {
        private String driverClass;
        private String jdbcUrl;
        private String user;
        private String password;
        private String maxPoolSize;
        private String maxIdleTIme;
        private String minPoolSize;

        public String getDriverClass() {
            return driverClass;
        }

        public void setDriverClass(String driverClass) {
            this.driverClass = driverClass;
        }

        public String getJdbcUrl() {
            return jdbcUrl;
        }

        public void setJdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(String maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public String getMaxIdleTIme() {
            return maxIdleTIme;
        }

        public void setMaxIdleTIme(String maxIdleTIme) {
            this.maxIdleTIme = maxIdleTIme;
        }

        public String getMinPoolSize() {
            return minPoolSize;
        }

        public void setMinPoolSize(String minPoolSize) {
            this.minPoolSize = minPoolSize;
        }

        public String getInitalPoolSize() {
            return initalPoolSize;
        }

        public void setInitalPoolSize(String initalPoolSize) {
            this.initalPoolSize = initalPoolSize;
        }

        private String initalPoolSize;
    }


}
