package com.robot2.user.service;

import com.robot2.user.entity.Org;
import com.robot2.user.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface RobotUserService {
    String getUserList(String message);
    String addUser(String message);
    String updateUser(String message);
    String deleteUser(String message);
    UserInfo userLogin(String message);
    String getOrg(String orgId);
    String getOrgList();
    String resetPassword(Map<String,String> map);
}
