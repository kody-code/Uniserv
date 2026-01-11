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

/**
 * <p>
 * 账户表
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("account_books")
public class AccountBooks {

    /**
     * 账户ID
     */
    @TableId(value = "account_id", type = IdType.AUTO)
    private Integer accountId;

    /**
     * 账户名称
     */
    @TableField("account_name")
    private String accountName;

    /**
     * 账户余额
     */
    @TableField("account_balance")
    private BigDecimal accountBalance;

    /**
     * 账户状态
     */
    @TableField("account_status")
    private Boolean accountStatus;

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
