package com.gateway.tool;

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
                map.put("data","账户已存在，请更换账户名！");
                map.put("code",1003);
                break;
            case 4:
                map.put("data","手机号错误，认证失败");
                map.put("code",1004);
                break;
            case 5:
                map.put("data","该信息已处理");
                map.put("code",1005);
        }
        return gson.toJson(map);

    }

}
