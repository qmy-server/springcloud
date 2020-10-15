package com.gateway.service;


import com.gateway.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


@FeignClient(value = "robot2-backstage-manage")
public interface AuthService {
    //登录
    @PostMapping("/user/userlogin")
    UserInfo userlogin(@RequestBody String message);
    //注册
    @PostMapping("/user/addUser")
    String addUser(@RequestBody String message);
}