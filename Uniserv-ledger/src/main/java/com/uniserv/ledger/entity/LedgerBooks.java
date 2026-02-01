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
 * 账本表，支持个人/家庭/办公/共享等多种类型，多币种配置
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("ledger_books")
public class LedgerBooks extends Model<LedgerBooks> {

    /**
     * 账本唯一标识，UUID格式，主键
     */
    @TableId(value = "book_id", type = IdType.AUTO)
    private UUID bookId;

    /**
     * 所属用户ID，关联sys_users表user_id字段，不可为NULL
     */
    @TableField("user_id")
    private UUID userId;

    /**
     * 账本名称，用户自定义，同一用户下未删除的账本名称不可重复
     */
    @TableField("book_name")
    private String bookName;

    /**
     * 账本类型，枚举类型：PERSONAL(个人)、FAMILY(家庭)、OFFICE(办公)、SHARE(共享)、OTHER(其他)，默认PERSONAL
     */
    @TableField("book_type")
    private String bookType;

    /**
     * 账本币种，遵循ISO 4217标准：CNY(人民币)、USD(美元)、EUR(欧元)等，默认CNY
     */
    @TableField("currency")
    private String currency;

    /**
     * 账本描述信息，用户自定义，说明账本用途，默认空字符串
     */
    @TableField("description")
    private String description;

    /**
     * 账本展示图标，前端图标类名：如icon-book(书本)、icon-wallet(钱包)，默认icon-book
     */
    @TableField("icon")
    private String icon;

    /**
     * 账本状态，true=启用（可新增交易），false=禁用（仅可查看），默认true
     */
    @TableField("status")
    private Boolean status;

    /**
     * 账本创建时间，自动填充当前时间，带时区
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 账本最后更新时间，带时区，通过触发器自动刷新
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
        return this.bookId;
    }
}
