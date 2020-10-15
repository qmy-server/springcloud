package com.robot2.user.mapper;

import com.google.gson.JsonObject;
import com.robot2.user.entity.Org;
import com.robot2.user.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("RobotUserMapper")

public interface RobotUserMapper {
    /**
     * 获取用户列表
     * @param username
     * @param id
     * @return
     */
    @Select("<script>select id,name,phone,sex,email,orgId,username,role from tbl_info_user where 1=1" +
            "<if test=\"username!=null and username!=''\">\n" +
            "AND username=#{username}\n" +
            "</if>\n" +
            "<if test=\"id!=null and id!=''\">\n" +
            "AND id=#{id}\n" +
            "</if>\n" +
            "</script>")
    List<UserInfo> getUserList(String username, String id);
    /**
     * 添加注册用户
     * @param userInfo
     * @return
     */
    @Insert("INSERT INTO tbl_info_user ( name, phone, sex, email, orgId, username, password, role, salt )\n" +
            "VALUES\n" +
            "\t( #{name}, #{phone}, #{sex}, #{email}, #{orgId}, #{username}, #{password}, #{role}, #{salt} )")
    int addUser(UserInfo userInfo);

    /**
     * 更改用户信息
     * @param userInfo
     * @return
     */
    @Update("UPDATE tbl_info_user SET name=#{name}, phone=#{phone}, sex=#{sex}, email=#{email}" +
            " where username = #{username}")
    int updateUser(UserInfo userInfo);

    /**
     * 通过username获取人员数量
     * @param username
     * @return
     */
    @Select("select count(*) from tbl_info_user where 1=1 and username = #{username}")
    int getUserByUserName(String username);

    /**
     * 通过id获取人员数量
     * @param id
     * @return
     */
    @Select("select count(*) from tbl_info_user where 1=1 and  id= #{id}")
    int getUserById(String id);

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Delete("DELETE FROM tbl_info_user where id = #{id}")
    int deleteUser(String id);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Select("select a.id,a.name,a.phone,a.sex,a.email,a.orgId,a.username,a.role,b.Name as orgName from tbl_info_user a,tbl_org b \n" +
            " where  a.orgId = b.id and a.username = #{username} and a.password = #{password}\n")
    UserInfo userLogin(String username, String password);

    /**
     * 登录获取salt
     * @param username
     * @return
     */
    @Select("select salt from tbl_info_user \n" +
            " where username = #{username}\n")
    String getSalt(String username);

    /**
     * 查找所有的单位
     * @return
     */
    @Select("SELECT Id,Name,Pid,Pname FROM tbl_org")
    List<Org> getOrg();

    /**
     * 重置密码
     * @param password
     * @return
     */
    @Update("update tbl_info_user set password = #{password},salt = #{salt} where username = #{username} ")
    int resetPassword(String password,String username,String salt);

    /**
     * 获取密码
     * @param map
     * @return
     */
    @Select("select password from tbl_info_user where 1=1 and username = #{username}")
    String dataPassword(Map<String, String> map);
}
