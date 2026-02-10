# NexStockCore - StockWise 后端服务

基于 Spring Boot 3 + Java 17，按《StockWise 后端开发范式》实现，与前端 NexStock 接口约定一致。

## 运行

```bash
cd NexStockCore
mvn spring-boot:run
```

- 端口：8080  
- 接口前缀：`/stockwise`（如 `http://localhost:8080/stockwise/auth/me`）

## 认证（auth 模块）

- **GET /stockwise/auth/me**：获取当前用户（需 Cookie 中的 JWT）
- **POST /stockwise/auth/login**：登录，请求体 `{ "usernameOrPhone": "string", "password": "string" }`，成功设置 HttpOnly Cookie
- **POST /stockwise/auth/logout**：登出，清除 Cookie

### 演示账号（内存用户）

| 账号   | 密码     | 角色   |
|--------|----------|--------|
| admin  | admin123 | admin  |
| user   | user123  | user   |

## 技术栈

- Spring Boot 3.2、Spring Web、Validation
- JJWT 0.12（JWT + HttpOnly Cookie）
- Maven、Java 17
