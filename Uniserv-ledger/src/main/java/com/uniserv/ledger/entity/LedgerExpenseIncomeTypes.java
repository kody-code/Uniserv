package com.uniserv.ledger.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 收入支出类型表（基础分类）
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("ledger_expense_income_types")
public class LedgerExpenseIncomeTypes extends Model<LedgerExpenseIncomeTypes> {

    /**
     * 类型ID（UUID）
     */
    @TableId(value = "types_id", type = IdType.AUTO)
    private UUID typesId;

    /**
     * 类型名称（如餐饮、工资）
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 类型类别（EXPENSE=支出，INCOME=收入）
     */
    @TableField("type_category")
    private String typeCategory;

    /**
     * 图标名称
     */
    @TableField("icon")
    private String icon;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 逻辑删除（false=未删除，true=已删除）
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 逻辑删除时间
     */
    @TableField("deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.typesId;
    }
}
