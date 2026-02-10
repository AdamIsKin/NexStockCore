package com.stockwise.auth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 系统用户实体，对应表 sys_user。
 * 使用 MyBatis-Plus 注解，id 采用 ASSIGN_UUID 由框架生成，避免自增与分布式冲突。
 */
@TableName("sys_user")
public class User {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /** 登录账号，唯一 */
    private String username;
    /** BCrypt 编码后的密码 */
    private String password;
    private String name;
    /** 角色：admin | user */
    private String role;
    private LocalDateTime createdAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
