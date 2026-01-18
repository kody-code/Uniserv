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
 * 账户表（用户资产账户）
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("ledger_account_books")
public class LedgerAccountBooks {

    /**
     * 账户ID（自增）
     */
    @TableId(value = "account_id", type = IdType.AUTO)
    private Integer accountId;

    /**
     * 关联用户 ID
     */
    @TableField("user_id")
    private UUID userId;

    /**
     * 账户名称（如支付宝、银行卡）
     */
    @TableField("account_name")
    private String accountName;

    /**
     * 账户余额（保留2位小数）
     */
    @TableField("account_balance")
    private BigDecimal accountBalance;

    /**
     * 账户状态（true=启用，false=禁用）
     */
    @TableField("account_status")
    private Boolean accountStatus;

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
}
