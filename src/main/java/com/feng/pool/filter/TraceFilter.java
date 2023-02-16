package com.feng.pool.filter;

import com.feng.pool.config.Constants;
import com.feng.pool.config.threadPool.TenantIdContext;
import com.feng.pool.config.threadPool.ThreadMdcUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author feng
 * @date 2022/12/13
 */
public class TraceFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request=(HttpServletRequest) req;
            String header = request.getHeader(Constants.TENANT_ID);
            if(Objects.nonNull(header)){
                TenantIdContext.setTenantId(header);
            }

            chain.doFilter(req, resp);
        } finally {
            ThreadMdcUtil.clear();
        }
    }
}
