package com.robot2.user.entity;

import lombok.Data;

import java.util.List;

@Data
public class Org {
    private String Id;
    private String Name;
    private String Pid;
    private String Pname;
    private List<Org> childList;

}
