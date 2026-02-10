package com.stockwise.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 分页结果，与《StockWise 前后端开发范式》一致，供列表接口统一使用。
 */
public class PageResult<T> {

    private List<T> list;
    private int page;
    private int pageSize;
    private long total;

    public static <T> PageResult<T> of(List<T> list, int page, int pageSize, long total) {
        PageResult<T> r = new PageResult<>();
        r.setList(list);
        r.setPage(page);
        r.setPageSize(pageSize);
        r.setTotal(total);
        return r;
    }

    public List<T> getList() { return list; }
    public void setList(List<T> list) { this.list = list; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
}
