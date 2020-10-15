package com.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.auth.JwtUtil;
import com.gateway.model.Account;
import com.gateway.model.UserInfo;
import com.gateway.tool.ReturnData;
import com.gateway.service.AuthService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 描述: 认证接口
 *
 * @Auther: qiemengyan
 */
@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private ObjectMapper objectMapper;

    @Value("${org.my.jwt.time}")
    private String effectiveTime;

    @Autowired
    private AuthService authService;


    public AuthController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 登陆认证接口
     * @param account
     * @return
     */
    @PostMapping("/login")
    public ReturnData login(@RequestBody Account account) throws Exception {
        Gson gson=new Gson();
        UserInfo ac=this.authService.userlogin(gson.toJson(account));
        if(ac!=null) {
            int effectivTimeInt = Integer.valueOf(effectiveTime.substring(0, effectiveTime.length() - 1));
            String effectivTimeUnit = effectiveTime.substring(effectiveTime.length() - 1, effectiveTime.length());
            String jwt = null;
            switch (effectivTimeUnit) {
                case "s": {
                    //秒
                    jwt = JwtUtil.createJWT("qmy", "0339", objectMapper.writeValueAsString(account), effectivTimeInt * 1000L);
                    break;
                }
                case "m": {
                    //分钟
                    jwt = JwtUtil.createJWT("qmy", "0339", objectMapper.writeValueAsString(account), effectivTimeInt * 60L * 1000L);
                    break;
                }
                case "h": {
                    //小时
                    jwt = JwtUtil.createJWT("qmy", "0339", objectMapper.writeValueAsString(account), effectivTimeInt * 60L * 60L * 1000L);
                    break;
                }
                case "d": {
                    //天
                    jwt = JwtUtil.createJWT("qmy", "0339", objectMapper.writeValueAsString(account), effectivTimeInt * 24L * 60L * 60L * 1000L);
                    break;
                }
            }
            return new ReturnData<String>(HttpStatus.SC_OK, "认证成功", jwt,ac);
           //return ResultTool.Success(ac);
        }else{
            return new ReturnData<String>(401, "认证失败，账户不属于本服务",null,null);
        }
    }

    /**
     * 为授权提示
     */
    @GetMapping("/unauthorized")
    public ReturnData<String> unauthorized(){
        return new ReturnData<String> (HttpStatus.SC_UNAUTHORIZED,"未认证,请重新登陆",null,null);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo){
        Gson gson=new Gson();
        return this.authService.addUser(gson.toJson(userInfo));
    }

}
