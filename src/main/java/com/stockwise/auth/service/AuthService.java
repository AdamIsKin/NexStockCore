package com.stockwise.auth.service;

import com.stockwise.auth.model.vo.CurrentUserVO;
import com.stockwise.common.exception.BusinessException;
import com.stockwise.common.ErrNo;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 认证业务：登录校验、当前用户查询。不依赖 Http 类型，Cookie 由 Controller 处理。
 * 当前为内存用户表，后续可改为 User 实体 + Repository。
 */
@Service
public class AuthService {

    private static final Map<String, StoredUser> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put("admin", new StoredUser("1", "admin", "admin123", "管理员", "admin"));
        USERS.put("user", new StoredUser("2", "user", "user123", "普通员工", "user"));
    }

    /**
     * 登录：账号/手机号 + 密码，返回当前用户 VO；失败抛 BusinessException(40001)。
     */
    public CurrentUserVO login(String usernameOrPhone, String password) {
        String key = usernameOrPhone != null ? usernameOrPhone.trim() : "";
        StoredUser u = USERS.get(key);
        if (u == null || !u.password.equals(password != null ? password : "")) {
            throw new BusinessException(ErrNo.PARAM_INVALID, "账号或密码错误");
        }
        CurrentUserVO vo = new CurrentUserVO();
        vo.setUserId(u.userId);
        vo.setName(u.name);
        vo.setRole(u.role);
        return vo;
    }

    /**
     * 根据 userId 获取当前用户；不存在返回 null（由 Controller 转为 41001）。
     */
    public CurrentUserVO getCurrentUser(String userId) {
        if (userId == null || userId.isBlank()) return null;
        for (StoredUser u : USERS.values()) {
            if (u.userId.equals(userId)) {
                CurrentUserVO vo = new CurrentUserVO();
                vo.setUserId(u.userId);
                vo.setName(u.name);
                vo.setRole(u.role);
                return vo;
            }
        }
        return null;
    }

    private static class StoredUser {
        final String userId;
        final String username;
        final String password;
        final String name;
        final String role;

        StoredUser(String userId, String username, String password, String name, String role) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.name = name;
            this.role = role;
        }
    }
}
