package com.stockwise.inventory.controller;

import com.stockwise.common.ApiResponse;
import com.stockwise.common.ApiResponses;
import com.stockwise.common.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * 库存/商品接口占位：前端已调用 GET /inventory/products 等，后端暂未实现业务，
 * 先返回空分页避免 NoResourceFoundException；后续接入 MyBatis-Plus 与业务表后再实现。
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    /**
     * 分页查询商品列表（占位）：返回空列表，与前端 PageResult 结构一致。
     */
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<PageResult<?>>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status) {
        PageResult<?> result = PageResult.of(Collections.emptyList(), page, pageSize, 0L);
        return ResponseEntity.ok(ApiResponses.success(result));
    }
}
