package com.stockwise.auth.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * POST /auth/login 请求体，与《StockWise 前后端开发范式》一致。
 */
public class LoginRequest {

    @NotBlank(message = "请输入手机号或账号")
    private String usernameOrPhone;

    @NotBlank(message = "请输入密码")
    private String password;

    public String getUsernameOrPhone() { return usernameOrPhone; }
    public void setUsernameOrPhone(String usernameOrPhone) { this.usernameOrPhone = usernameOrPhone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
