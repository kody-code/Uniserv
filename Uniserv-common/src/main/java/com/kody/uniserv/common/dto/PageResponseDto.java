package com.kody.uniserv.common.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 分页响应DTO
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "分页响应数据")
public class PageResponseDto<T> {

    /**
     * 总记录数
     */
    @Schema(description = "总记录数", example = "100")
    private Long total;

    /**
     * 当前页码
     */
    @Schema(description = "当前页码", example = "1")
    private Long pageNum;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数", example = "10")
    private Long pageSize;

    /**
     * 总页数
     */
    @Schema(description = "总页数", example = "10")
    private Long pages;

    /**
     * 数据列表
     */
    @Schema(description = "数据列表")
    private List<T> records;


    /**
     * 构造函数 - 从MyBatis-Plus Page对象构建
     *
     * @param page MyBatis-Plus分页对象
     */
    public PageResponseDto(Page<T> page) {
        this.total = page.getTotal();
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.pages = page.getPages();
        this.records = page.getRecords();
    }
}