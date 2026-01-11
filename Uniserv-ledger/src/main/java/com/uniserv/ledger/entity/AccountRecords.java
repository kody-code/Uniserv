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
 * 账户记录表
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("account_records")
public class AccountRecords {

    /**
     * 记录ID
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    /**
     * 类型ID
     */
    @TableField("type_id")
    private Integer typeId;

    /**
     * 账户ID
     */
    @TableField("account_id")
    private Integer accountId;

    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 备注
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

    @TableField("deleted")
    private Boolean deleted;
}
