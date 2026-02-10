package com.stockwise.auth.model.vo;

/**
 * 当前用户出参，与前端 CurrentUser 一致，用于 /auth/me 与 /auth/login 的 Data 字段。
 */
public class CurrentUserVO {

    private String userId;
    private String name;
    private String role; // "admin" | "user"

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
