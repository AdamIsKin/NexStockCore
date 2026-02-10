package com.stockwise.auth.service;

import com.stockwise.auth.model.entity.User;
import com.stockwise.auth.model.vo.CurrentUserVO;
import com.stockwise.auth.repository.UserMapper;
import com.stockwise.common.exception.BusinessException;
import com.stockwise.common.ErrNo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证业务：登录校验、当前用户查询。不依赖 Http 类型，Cookie 由 Controller 处理。
 * 用户数据来自 MySQL（MyBatis-Plus），密码使用 BCrypt 校验。
 */
@Service
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 登录：账号/手机号 + 密码，返回当前用户 VO；失败抛 BusinessException(40001)。
     * 先判空再调用 matches，避免 password 为 null 传入 BCrypt 导致 NPE；空用户名提前返回避免无意义查库。
     */
    public CurrentUserVO login(String usernameOrPhone, String password) {
        String username = usernameOrPhone != null ? usernameOrPhone.trim() : "";
        if (username.isEmpty()) {
            throw new BusinessException(ErrNo.PARAM_INVALID, "账号或密码错误");
        }
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException(ErrNo.PARAM_INVALID, "账号或密码错误");
        }
        if (password == null || password.isBlank()) {
            throw new BusinessException(ErrNo.PARAM_INVALID, "账号或密码错误");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ErrNo.PARAM_INVALID, "账号或密码错误");
        }
        return toCurrentUserVO(user);
    }

    /**
     * 根据 userId 获取当前用户；不存在返回 null（由 Controller 转为 41001）。
     */
    public CurrentUserVO getCurrentUser(String userId) {
        if (userId == null || userId.isBlank()) return null;
        User user = userMapper.selectById(userId);
        return user == null ? null : toCurrentUserVO(user);
    }

    private static CurrentUserVO toCurrentUserVO(User user) {
        CurrentUserVO vo = new CurrentUserVO();
        vo.setUserId(user.getId());
        vo.setName(user.getName());
        vo.setRole(user.getRole());
        return vo;
    }
}
