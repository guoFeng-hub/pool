package com.feng.pool.controller;

import com.feng.pool.config.DateSourcePropertyMap;
import com.feng.pool.entry.User;
import com.feng.pool.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    private List<User> queryUser() {
        return userService.queryUser();
    }

   @GetMapping("ValueConstants")
    private Object getParam() {
       return dataSourceConfig;
    }
}
