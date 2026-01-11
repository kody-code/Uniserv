package com.uniserv.recipe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * <p>
 * 食材表
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("ingredients")
public class Ingredients {

    /**
     * 食材ID
     */
    @TableId(value = "ingredient_id", type = IdType.AUTO)
    private Integer ingredientId;

    /**
     * 食材名称
     */
    @TableField("ingredient_name")
    private String ingredientName;

    /**
     * 食材单位
     */
    @TableField("unit")
    private String unit;
}
