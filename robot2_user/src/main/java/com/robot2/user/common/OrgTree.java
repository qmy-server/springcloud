package com.robot2.user.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.robot2.user.entity.Org;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 类名称：OrgTree
 * 类描述：递归构造树型结构
 */
@Slf4j
@Service
public class OrgTree {

    public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();
    public List<Org> menuCommon;


    public List<Object> menuList(List<Org> menu,String orgId){
        List<Object> list = new ArrayList<Object>();
        this.menuCommon = menu;
        for (Org x : menu) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            log.info(x.getPid());
            if(orgId.equals(x.getId())){
                mapArr.put("id", x.getId());
                mapArr.put("label", x.getName());
                mapArr.put("pid", x.getPid());
                mapArr.put("children", menuChild(x.getId()));
                list.add(mapArr);
            }
        }
        return list;
    }

    public List<?> menuChild(String id){
        List<Object> lists = new ArrayList<Object>();
        for(Org a:menuCommon){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            log.info(a.getPid());
            if(id.equals(a.getPid())){
                childArray.put("id", a.getId());
                childArray.put("label", a.getName());
                childArray.put("pid", a.getPid());
                childArray.put("children", menuChild(a.getId()));
                lists.add(childArray);
            }
        }
        return lists;
    }

}