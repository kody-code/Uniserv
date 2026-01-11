package com.uniserv.recipe.entity;

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
 * 菜谱表
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Getter
@Setter
@ToString
@TableName("recipes")
@Accessors(chain = true)
public class Recipes {

    /**
     * 菜谱ID
     */
    @TableId(value = "recipe_id", type = IdType.AUTO)
    private Integer recipeId;

    /**
     * 菜谱名称
     */
    @TableField("recipe_name")
    private String recipeName;

    /**
     * 烹饪步骤
     */
    @TableField("cooking_steps")
    private String cookingSteps;

    /**
     * 烹饪时间
     */
    @TableField("cooking_time")
    private Integer cookingTime;

    /**
     * 难度
     */
    @TableField("difficulty")
    private String difficulty;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
