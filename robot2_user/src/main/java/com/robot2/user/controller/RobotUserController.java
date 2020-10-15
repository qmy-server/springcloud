package com.robot2.user.controller;

import com.robot2.user.entity.Org;
import com.robot2.user.entity.UserInfo;
import com.robot2.user.service.RobotUserService;
import com.robot2.user.tools.ResultTool;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户登录注册服务
 */
@RestController
@RequestMapping("/user")
public class RobotUserController {
    @Resource
    RobotUserService robotUserService;

    /**
     * 获取用户列表
     * @param message
     * @return
     */
    @GetMapping("/userList")
    public String getUserList(@RequestBody String message){
        return robotUserService.getUserList(message);
    }

    /**
     * 添加注册用户
     * @param message
     * @return
     */
    @PostMapping("/addUser")
    public String addUser(@RequestBody String message){
        return robotUserService.addUser(message);
    }

    /**
     * 更改用户信息
     * @param message
     * @return
     */
    @PostMapping("/updateUser")
    public String updateUser(@RequestBody String message){
        return robotUserService.updateUser(message);
    }

    /**
     * 删除用户
     * @param message
     * @return
     */
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestBody String message){
        return robotUserService.deleteUser(message);
    }

    /**
     * 用户登录
     * @param message
     * @return
     */
    @PostMapping("/userlogin")
    public UserInfo userLogin(@RequestBody String message){
        return robotUserService.userLogin(message);
    }

    /**
     * 递归构造树型结构
     * @param map
     * @return
     */
    @PostMapping("/getOrg")
    public String getOrg(@RequestBody Map<String,String> map){
        if(map.get("orgId")!=null){
            return robotUserService.getOrg(map.get("orgId"));
        }else{
            return ResultTool.Error(1);
        }
    }

    @PostMapping("/getOrgList")
    public String getOrgList(){
        return robotUserService.getOrgList();
    }

    /**
     * 重置密码
     * @param map
     * @return
     */
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody Map<String, String> map) {
        if (map.get("username") == null || "".equals(map.get("username")))
            return ResultTool.Error(13);
        if (map.get("oldPassword") == null || "".equals(map.get("oldPassword")))
            return ResultTool.Error(14);
        if (map.get("newPassword") == null || "".equals(map.get("newPassword")))
            return ResultTool.Error(15);
        return robotUserService.resetPassword(map);
    }

}
