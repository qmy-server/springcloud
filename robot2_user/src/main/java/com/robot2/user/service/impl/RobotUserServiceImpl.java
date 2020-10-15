package com.robot2.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.robot2.user.common.OrgTree;
import com.robot2.user.entity.Org;
import com.robot2.user.entity.UserInfo;
import com.robot2.user.mapper.RobotUserMapper;
import com.robot2.user.service.RobotUserService;
import com.robot2.user.tools.ResultTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;


@Service("RobotUserService")
@Slf4j
public class RobotUserServiceImpl implements RobotUserService {
    @Autowired
    RobotUserMapper robotUserMapper;

    /**
     * 获取用户列表
     *
     * @param message
     * @return
     */
    @Override
    public String getUserList(String message) {
        JSONObject jsonMessage = JSONObject.parseObject(message);
        String username = jsonMessage.getString("username");
        String id = jsonMessage.getString("id");
        List<UserInfo> userInfo = robotUserMapper.getUserList(username, id);
        if (userInfo.size() > 0) {
            return ResultTool.Success(robotUserMapper.getUserList(username, id));
        } else {
            return ResultTool.Error(9);
        }
    }

    /**
     * 添加用户
     *
     * @param message
     * @return
     */
    @Override
    public String addUser(String message) {
        JSONObject jsonMessage = JSONObject.parseObject(message);
        String paramUserName = jsonMessage.getString("username");
        String paramPassword = jsonMessage.getString("password");
        if (paramUserName != null && !"".equals(paramUserName)) {
            Integer userName = robotUserMapper.getUserByUserName(paramUserName);
            if (userName == 0) {
                if (paramUserName.equals("robot")) {
                    UserInfo userInfo = JSONObject.toJavaObject(jsonMessage, UserInfo.class);
                    robotUserMapper.addUser(userInfo);
                } else {
                    if (!"".equals(paramPassword) && paramPassword != null) {
//                        //md5加密
//                        String salt = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
//                        String saltPassword = paramPassword.concat(salt);
//                        String md5Password = DigestUtils.md5DigestAsHex(saltPassword.getBytes());
//                        UserInfo userInfo = JSONObject.toJavaObject(jsonMessage, UserInfo.class);
//                        userInfo.setSalt(salt);
//                        userInfo.setPassword(md5Password);
                        //BCrypt加密
                        String salt = BCrypt.gensalt();
                        String BCryptPassword = BCrypt.hashpw(paramPassword, salt);
                        UserInfo userInfo = JSONObject.toJavaObject(jsonMessage, UserInfo.class);
                        userInfo.setSalt(salt);
                        userInfo.setPassword(BCryptPassword);
                        robotUserMapper.addUser(userInfo);
                    } else {
                        return ResultTool.Error(11);
                    }
                }
            } else {
                return ResultTool.Error(8);
            }
        } else {
            return ResultTool.Error(1);
        }
        return ResultTool.Success("insert success!");
    }

    /**
     * 更改用户信息
     *
     * @param message
     * @return
     */
    @Override
    public String updateUser(String message) {
        JSONObject jsonMessage = JSONObject.parseObject(message);
        String paramUserName = jsonMessage.getString("username");
        if (paramUserName != null && !"".equals(paramUserName)) {
            Integer userName = robotUserMapper.getUserByUserName(paramUserName);
            if (userName > 0) {
                UserInfo userInfo = JSONObject.toJavaObject(jsonMessage, UserInfo.class);
                robotUserMapper.updateUser(userInfo);
            } else {
                return ResultTool.Error(9);
            }
        } else {
            return ResultTool.Error(1);
        }
        return ResultTool.Success("update success!");
    }

    /**
     * 删除用户
     *
     * @param Message
     * @return
     */
    @Override
    public String deleteUser(String Message) {
        JSONObject jsStr = JSONObject.parseObject(Message);
        JSONArray jsonId = jsStr.getJSONArray("id");
        Integer userId;
        String intId;
        if (jsonId != null) {
            if (jsonId.size() > 0) {
                for (int i = 0; i < jsonId.size(); i++) {
                    intId = jsonId.get(i).toString();
                    userId = robotUserMapper.getUserById(intId);
                    if (userId > 0) {
                        robotUserMapper.deleteUser(intId);
                        log.info("id为：" + intId + " 的用户已删除！");
                    } else {
                        log.info("id为：" + intId + " 的用户不存在！");
                    }
                }
            } else {
                return ResultTool.Error(1);
            }
        } else {
            return ResultTool.Error(1);
        }
        return ResultTool.Success("delete success!");
    }

    /**
     * 用户登录
     *
     * @param message
     * @return
     */
    @Override
    public UserInfo userLogin(String message) {
        JSONObject jsonMessage = JSONObject.parseObject(message);
        String username = jsonMessage.getString("username");
        String password = jsonMessage.getString("password");
        UserInfo userInfo;
        if ("".equals(username) || username == null)
            return null;
        if (username.equals("robot")) {
            userInfo = robotUserMapper.userLogin(username, password);
        } else {
            String salt = robotUserMapper.getSalt(username);
            if("".equals(password) || password == null)
                return null;
            //md5加密
//            String saltPassword = password.concat(salt);
//            String md5Password = DigestUtils.md5DigestAsHex(saltPassword.getBytes());
//            userInfo = robotUserMapper.userLogin(username, md5Password);
            //BCrypt加密
            String BCryptPassword = BCrypt.hashpw(password, salt);
            userInfo = robotUserMapper.userLogin(username, BCryptPassword);
        }
        if (userInfo != null) {
            return userInfo;
        } else {
            return null;
        }
    }

    @Autowired
    OrgTree orgTree;

    /**
     * 递归构造树型结构
     *
     * @param orgId
     * @return
     */
    @Override
    public String getOrg(String orgId) {
        List<Org> OrgList = robotUserMapper.getOrg();
        List<Object> listOrg = orgTree.menuList(OrgList, orgId);
        if (listOrg.size() > 0) {
            log.info(listOrg.toString());
            Gson gson = new Gson();
            String orgList = gson.toJson(listOrg);
            return orgList;
        } else {
            return ResultTool.Error(12);
        }
    }

    /**
     * 获取所有机构列表
     *
     * @return
     */
    @Override
    public String getOrgList() {
        List<Org> listOrg = robotUserMapper.getOrg();
        if (listOrg.size() > 0) {
            Gson gson = new Gson();
            String orgList = gson.toJson(listOrg);
            return orgList;
        } else {
            return ResultTool.Error(12);
        }
    }

    /**
     * 重置密码
     *
     * @param map
     * @return
     */
    @Override
    public String resetPassword(Map<String, String> map) {
        String dataPassword = robotUserMapper.dataPassword(map);
        if (dataPassword == null || "".equals(dataPassword))
            return ResultTool.Error(9);
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        String username = map.get("username");
        String salt = robotUserMapper.getSalt(username);
//        String newSalt = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        String newSalt = BCrypt.gensalt();
        String password = newPassword;
        if (!username.equals("robot")) {
            //md5加密
//            String oldPasswordMd5 = DigestUtils.md5DigestAsHex((oldPassword.concat(salt)).getBytes());
//            if (!dataPassword.equals(oldPasswordMd5))
//                return ResultTool.Error(16);
//            password = DigestUtils.md5DigestAsHex((newPassword.concat(newSalt)).getBytes());
            //BCrypt加密
            String oldBCryptPassword = BCrypt.hashpw(oldPassword, salt);
            if (!dataPassword.equals(oldBCryptPassword))
                return ResultTool.Error(16);
            password = BCrypt.hashpw(newPassword, newSalt);
        } else {
            newSalt = null;
            if (!dataPassword.equals(oldPassword))
                return ResultTool.Error(16);
        }
        robotUserMapper.resetPassword(password, username, newSalt);
        return ResultTool.Success("密码已经重置!");
    }
}
