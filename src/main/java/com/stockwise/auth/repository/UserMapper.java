package com.stockwise.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stockwise.auth.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户表 Mapper，继承 BaseMapper 获得单表 CRUD；按 username 查询用于登录。
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM sys_user WHERE username = #{username} LIMIT 1")
    User selectByUsername(@Param("username") String username);
}
