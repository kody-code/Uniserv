package com.uniserv.recipes.entity;

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
 * 食材表
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("recipes_ingredients")
public class RecipesIngredients {

    /**
     * 食材ID（自增）
     */
    @TableId(value = "ingredient_id", type = IdType.AUTO)
    private Integer ingredientId;

    /**
     * 食材名称（唯一）
     */
    @TableField("ingredient_name")
    private String ingredientName;

    /**
     * 食材单位（如克、毫升、个、勺）
     */
    @TableField("unit")
    private String unit;

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
