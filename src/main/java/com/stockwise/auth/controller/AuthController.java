package com.stockwise.auth.controller;

import com.stockwise.auth.model.dto.LoginRequest;
import com.stockwise.auth.model.vo.CurrentUserVO;
import com.stockwise.auth.service.AuthService;
import com.stockwise.common.ApiResponse;
import com.stockwise.common.ApiResponses;
import com.stockwise.common.ErrNo;
import com.stockwise.config.JwtSupport;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 认证接口：/auth/me、/auth/login、/auth/logout，与《StockWise 前后端开发范式》一致。
 * context-path=/stockwise，故完整路径为 /stockwise/auth/me 等。
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtSupport jwtSupport;

    public AuthController(AuthService authService, JwtSupport jwtSupport) {
        this.authService = authService;
        this.jwtSupport = jwtSupport;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<CurrentUserVO>> me(HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        CurrentUserVO user = token != null ? jwtSupport.parseToken(token) : null;
        if (user == null) {
            return ResponseEntity.ok(ApiResponses.error(ErrNo.UNAUTHENTICATED, "未登录或登录已过期"));
        }
        CurrentUserVO current = authService.getCurrentUser(user.getUserId());
        if (current == null) {
            return ResponseEntity.ok(ApiResponses.error(ErrNo.UNAUTHENTICATED, "未登录或登录已过期"));
        }
        return ResponseEntity.ok(ApiResponses.success(current));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<CurrentUserVO>> login(
            @Valid @RequestBody LoginRequest body,
            HttpServletResponse response) {
        CurrentUserVO user = authService.login(body.getUsernameOrPhone(), body.getPassword());
        String token = jwtSupport.createToken(user);
        Cookie cookie = new Cookie(jwtSupport.getCookieName(), token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(jwtSupport.getCookieMaxAgeSeconds());
        response.addCookie(cookie);
        return ResponseEntity.ok(ApiResponses.success(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(jwtSupport.getCookieName(), "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok(ApiResponses.success(null));
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        String name = jwtSupport.getCookieName();
        for (Cookie c : cookies) {
            if (name.equals(c.getName()) && c.getValue() != null && !c.getValue().isBlank()) {
                return c.getValue();
            }
        }
        return null;
    }
}
