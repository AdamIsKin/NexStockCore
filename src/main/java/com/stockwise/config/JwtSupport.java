package com.stockwise.config;

import com.stockwise.auth.model.vo.CurrentUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 生成与解析，仅存 userId/name/role，与 CurrentUserVO 一致；Cookie 名与过期时间从 JwtProperties 读取。
 */
@Component
public class JwtSupport {

    private final JwtProperties props;
    private final SecretKey key;

    public JwtSupport(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(CurrentUserVO user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + props.getExpirationMs());
        return Jwts.builder()
                .subject(user.getUserId())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    public CurrentUserVO parseToken(String token) {
        if (token == null || token.isBlank()) return null;
        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            CurrentUserVO vo = new CurrentUserVO();
            vo.setUserId(claims.getSubject());
            vo.setName((String) claims.get("name"));
            vo.setRole((String) claims.get("role"));
            return vo;
        } catch (Exception e) {
            return null;
        }
    }

    public String getCookieName() {
        return props.getCookieName();
    }

    public int getCookieMaxAgeSeconds() {
        return (int) (props.getExpirationMs() / 1000);
    }
}
