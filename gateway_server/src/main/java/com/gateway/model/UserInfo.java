package com.gateway.model;

import lombok.Data;

@Data
public class UserInfo {
    private Integer id;
    private String name;
    private String phone;
    private boolean sex;
    private String email;
    private String orgId;
    private String username;
    private String password;
    private Integer role;
    private String salt;
    private String orgName;
}
