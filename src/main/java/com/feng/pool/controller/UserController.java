package com.feng.pool.controller;

import com.feng.pool.config.DateSourcePropertyMap;
import com.feng.pool.config.redis.RedisRegister;
import com.feng.pool.entry.User;
import com.feng.pool.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private Map<String, DateSourcePropertyMap> dataSourceConfig;

    @Resource
    private RedisRegister redisRegister;

    @GetMapping
    private List<User> queryUser() {
        return userService.queryUser();
    }

    @GetMapping(value = "getRedis/{key}")
    private String getRedis(@PathVariable String key) {
        RedisTemplate redisTemplate = redisRegister.getRedisTemplate(key);
        return (String) redisTemplate.opsForValue().get("name");
    }
   @GetMapping("ValueConstants")
    private Object getParam() {
       return dataSourceConfig;
    }


}
