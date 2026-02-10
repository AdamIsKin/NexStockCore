package com.stockwise.auth.runner;

import com.stockwise.auth.model.entity.User;
import com.stockwise.auth.repository.UserMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用启动后若 sys_user 表为空，则插入初始用户 admin / user，密码为 BCrypt 编码。
 */
@Component
public class InitUserRunner implements ApplicationRunner {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public InitUserRunner(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(ApplicationArguments args) {
        if (userMapper.selectCount(null) > 0) return;
        insertUser("admin", "admin123", "管理员", "admin");
        insertUser("user", "user123", "普通员工", "user");
    }

    private void insertUser(String username, String rawPassword, String name, String role) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setName(name);
        u.setRole(role);
        userMapper.insert(u);
    }
}
