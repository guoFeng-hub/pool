package com.feng.pool.config;

import com.feng.pool.config.threadPool.TenantIdContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        return TenantIdContext.getTenantId();
    }


}
