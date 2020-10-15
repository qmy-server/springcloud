package com.robot2.user.tools;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ResultTool {
    //返回成功的结果
    public static String Success(Object data){
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<>();
        map.put("code",200);
        map.put("data",data);
        return gson.toJson(map);
    }
    public static String SuccessUser(Object data){
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<>();
        map.put("code",200);
        map.put("data",data);
        return gson.toJson(map);
    }
    //返回错误的结果
    public static String Error(int type){
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<>();
        switch (type){
            case 1:
                map.put("data","传入参数为空！");
                map.put("code",1001);
                break;
            case 2:
                map.put("data","查询到的数据为空！");
                map.put("code",1002);
                break;
            case 3:
                map.put("data","其他错误");
                map.put("code",1003);
                break;
            case 4:
                map.put("data","账号或密码错误");
                map.put("code",1004);
                break;
            case 5:
                map.put("data","该信息已处理");
                map.put("code",1005);
                break;
            case 6:
                map.put("data","该对象不是json类型");
                map.put("code",1006);
                break;
            case 7:
                map.put("data","该对象中缺少参数");
                map.put("code",1007);
                break;
            case 8:
                map.put("data","该用户已经存在!");
                map.put("code",1008);
                break;
            case 9:
                map.put("data","该用户不存在!");
                map.put("code",1009);
                break;
            case 10:
                map.put("data","用户名或密码错误!");
                map.put("code",1010);
                break;
            case 11:
                map.put("data","用户名和密码不能为空!");
                map.put("code",1011);
                break;
            case 12:
                map.put("data","机构不存在!");
                map.put("code",1012);
                break;
            case 13:
                map.put("data","用户名不能为空!");
                map.put("code",1013);
                break;
            case 14:
                map.put("data","原始密码不能为空!");
                map.put("code",1014);
                break;
            case 15:
                map.put("data","新密码不能为空!");
                map.put("code",1015);
                break;
            case 16:
                map.put("data","原始密码不正确!");
                map.put("code",1016);
                break;
            default:
                map.put("data","未知错误类型");
                map.put("code", 0);


        }
        return gson.toJson(map);

    }

    //返回成功的结果
    public static String Successful(Object data){
        Map<String,Object> map=new HashMap<>();
        map.put("code",1);
        map.put("data",data);
        return map.toString();
    }

    //返回错误的结果String
    public static String Errors(int type){
        Map<String,Object> map=new HashMap<>();
        switch (type){
            case 1:
                map.put("data","传入参数为空！");
                map.put("code",1001);
                break;
            case 2:
                map.put("data","查询到的数据为空！");
                map.put("code",1002);
                break;
            case 3:
                map.put("data","其他错误");
                map.put("code",1003);
                break;
            case 4:
                map.put("data","账号或密码错误");
                map.put("code",1004);
                break;
            case 5:
                map.put("data","该信息已处理");
                map.put("code",1005);
                break;
            case 6:
                map.put("data","该对象不是json类型");
                map.put("code",1006);
                break;
            default:
                map.put("data","未知错误类型");
                map.put("code", 0);

        }
        return map.toString();

    }

}
