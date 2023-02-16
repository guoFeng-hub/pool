package com.feng.pool.service.impl;

import com.feng.pool.config.threadPool.TenantIdContext;
import com.feng.pool.entry.User;
import com.feng.pool.mapper.UserMapper;
import com.feng.pool.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<User> queryUser() {
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<User> users = userMapper.selectUser();
            users.forEach(e -> LOGGER.info("第一次"+e.getName()));
        }, threadPoolTaskExecutor);

        TenantIdContext.setTenantId("pool-b");
        List<User> users = userMapper.selectUser();
        users.forEach(e -> LOGGER.info("第二次"+e.getName()));

        voidCompletableFuture.join();
        TenantIdContext.setTenantId("pool-a");
        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<User> users1 = userMapper.selectUser();
            users1.forEach(e -> LOGGER.info("第三次" + e.getName()));
        }, threadPoolTaskExecutor);
        voidCompletableFuture1.join();

        TenantIdContext.setTenantId("pool-b");
        threadPoolTaskExecutor.execute(()->{
            try {
                Thread.sleep(8000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<User> users1 = userMapper.selectUser();
            users1.forEach(e -> LOGGER.info("第四次"+e.getName()));
        });

        TenantIdContext.setTenantId("pool-a");
        threadPoolTaskExecutor.execute(()->{
            try {
                Thread.sleep(7000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<User> users2 = userMapper.selectUser();
            users2.forEach(e -> LOGGER.info("第五次"+e.getName()));
        });

        voidCompletableFuture1.join();
        return users;
    }
}
