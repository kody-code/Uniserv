package com.uniserv.ledger.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 收入支出类型表
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("expense_income_types")
public class ExpenseIncomeTypes {

    /**
     * 类型ID
     */
    @TableId(value = "types_id", type = IdType.AUTO)
    private Integer typesId;

    /**
     * 类型名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 类型类别
     */
    @TableField("type_category")
    private String typeCategory;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private Boolean deleted;
}
