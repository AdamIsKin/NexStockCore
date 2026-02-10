package com.stockwise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * 排除 UserDetailsServiceAutoConfiguration：认证由 JWT + AuthController 完成，
 * 不需要 Spring 生成默认密码，避免启动时打印 "Using generated security password" 警告。
 */
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
@MapperScan("com.stockwise.auth.repository")
public class NexStockCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexStockCoreApplication.class, args);
    }
}
