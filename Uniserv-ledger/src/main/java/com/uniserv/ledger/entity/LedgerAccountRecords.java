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
 * 账本下的账户交易记录表，记录各类账户交易信息，支持收入、支出、转账
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("ledger_account_records")
public class LedgerAccountRecords extends Model<LedgerAccountRecords> {

    /**
     * 交易记录唯一标识，UUID格式，主键
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private UUID recordId;

    /**
     * 所属账户ID，关联ledger_account表account_id字段，不可为NULL
     */
    @TableField(value = "account_id")
    private UUID accountId;

    /**
     * 所属账本ID，关联ledger_books表book_id字段，冗余存储提升查询效率，不可为NULL
     */
    @TableField(value = "book_id")
    private UUID bookId;

    /**
     * 交易金额，数值类型保留2位小数：收入=正数，支出=负数，转账需关联两个账户生成等额正负记录，不可为0
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 交易类型，枚举类型：INCOME(收入)、EXPENSE(支出)、TRANSFER(转账)，不可为NULL
     */
    @TableField(value = "record_type")
    private String recordType;

    /**
     * 关联收支类型表的ID，不可为NULL
     */
    @TableField(value = "type_id")
    private UUID typeId;

    /**
     * 交易分类名称，用户自定义：如"餐饮美食"、"工资收入"、"房租支出"，默认空字符串
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 交易备注信息，用户自定义补充说明，默认空字符串
     */
    @TableField("description")
    private String description;

    /**
     * 交易发生时间，可自定义（如补录历史交易），带时区，用于分区表拆分，不可为NULL
     */
    @TableField(value = "occurred_at")
    private LocalDateTime occurredAt;

    /**
     * 交易记录创建时间，自动填充当前时间，带时区，不可手动修改
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 交易记录最后更新时间，带时区，通过触发器自动刷新
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 软删除标记，true=已删除，false=正常使用，默认false
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 软删除时间，仅当deleted=true时有值，带时区，通过触发器自动设置
     */
    @TableField(value = "deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.deletedAt;
    }
}
