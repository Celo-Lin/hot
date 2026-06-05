package com.hot.common.page;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 精简分页对象，仅保留 pageNum、pageSize、total、pages、list 五个字段。
 * 兼容 PageHelper：当传入的 list 为 PageHelper 分页查询结果（Page）时，自动提取分页信息。
 */
@Data
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前页码 */
    private int pageNum;
    /** 每页数量 */
    private int pageSize;
    /** 总记录数 */
    private long total;
    /** 总页数 */
    private int pages;
    /** 当前页数据 */
    private List<T> list;

    public PageInfo() {
    }

    public PageInfo(List<T> list) {
        this.list = list;
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
        } else if (list != null) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.total = list.size();
            this.pages = list.isEmpty() ? 0 : 1;
        }
    }
}
