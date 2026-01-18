package com.uniserv.ledger.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 账户记录表（收支明细）
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("ledger_account_records")
public class LedgerAccountRecords {

    /**
     * 记录ID（自增）
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    /**
     * 关联收支类型 ID
     */
    @TableField("type_id")
    private UUID typeId;

    /**
     * 关联账户ID
     */
    @TableField("account_id")
    private Integer accountId;

    /**
     * 关联用户ID
     */
    @TableField("user_id")
    private UUID userId;

    /**
     * 交易金额（支出为负，收入为正，保留2位小数）
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 交易备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 交易发生时间
     */
    @TableField("record_time")
    private LocalDateTime recordTime;

    /**
     * 记账录入时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

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
}
