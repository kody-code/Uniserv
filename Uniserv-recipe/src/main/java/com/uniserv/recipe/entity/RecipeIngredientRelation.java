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
 * 菜谱食材关系表
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("recipe_ingredient_relation")
public class RecipeIngredientRelation {

    /**
     * 关系ID
     */
    @TableId(value = "relation_id", type = IdType.AUTO)
    private Integer relationId;

    /**
     * 菜谱ID
     */
    @TableField("recipe_id")
    private Integer recipeId;

    /**
     * 食材ID
     */
    @TableField("ingredient_id")
    private Integer ingredientId;

    /**
     * 食材数量
     */
    @TableField("amount")
    private String amount;
}
