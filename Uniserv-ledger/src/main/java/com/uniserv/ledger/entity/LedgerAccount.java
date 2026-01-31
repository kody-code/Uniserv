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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 账本下的账户表，记录各类账户信息与余额，支持普通账户与信用账户
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("ledger_account")
public class LedgerAccount extends Model<LedgerAccount> {

    /**
     * 账户唯一标识，UUID格式，主键
     */
    @TableId(value = "account_id", type = IdType.AUTO)
    private UUID accountId;

    /**
     * 所属账本ID，关联ledger_books表book_id字段，不可为NULL
     */
    @TableField("book_id")
    private UUID bookId;

    /**
     * 账户名称，用户自定义：如"招商银行储蓄卡"、"招行信用卡"，不可为NULL
     */
    @TableField("account_name")
    private String accountName;

    /**
     * 账户类型，字符串标识：CASH(现金)、BANK(银行卡)、CREDIT_CARD(信用卡)、INVESTMENT(投资)等
     */
    @TableField("account_type")
    private String accountType;

    /**
     * 账户属性，枚举类型：NORMAL(普通账户，余额不可为负)、CREDIT(信用账户，余额可负)，默认NORMAL
     */
    @TableField("account_attribute")
    private String accountAttribute;

    /**
     * 账户余额，数值类型保留2位小数，普通账户≥0，信用账户可负，默认0.00
     */
    @TableField("account_balance")
    private BigDecimal accountBalance;

    /**
     * 账户币种，遵循ISO 4217标准，需与所属账本币种一致，默认CNY
     */
    @TableField("currency")
    private String currency;

    /**
     * 账户展示图标，前端图标类名：如icon-wallet(钱包)、icon-card(银行卡)，默认icon-wallet
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序值，整数类型，数值越小前端展示越靠前，默认0
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 账户创建时间，自动填充当前时间，带时区
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 账户最后更新时间，带时区，通过触发器自动刷新（含余额更新）
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 软删除标记，true=已删除，false=正常使用，默认false
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 软删除时间，仅当deleted=true时有值，带时区，通过触发器自动设置
     */
    @TableField("deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.accountId;
    }
}
