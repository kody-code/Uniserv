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
 * 菜谱食材关系表（多对多关联）
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("recipes_ingredient_relation")
public class RecipesIngredientRelation {

    /**
     * 关系ID（自增）
     */
    @TableId(value = "relation_id", type = IdType.AUTO)
    private Integer relationId;

    /**
     * 关联菜谱ID
     */
    @TableField("recipe_id")
    private Integer recipeId;

    /**
     * 关联食材ID
     */
    @TableField("ingredient_id")
    private Integer ingredientId;

    /**
     * 食材数量（如50克、2勺）
     */
    @TableField("amount")
    private String amount;

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
