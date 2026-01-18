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
import java.util.UUID;

/**
 * <p>
 * 菜谱表
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("recipes_entry")
public class RecipesEntry {

    /**
     * 菜谱ID（自增）
     */
    @TableId(value = "recipe_id", type = IdType.AUTO)
    private Integer recipeId;

    /**
     * 关联用户 ID
     */
    @TableField("user_id")
    private UUID userId;

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
     * 烹饪时间（分钟）
     */
    @TableField("cooking_time")
    private Integer cookingTime;

    /**
     * 难度（EASY=简单，MEDIUM=中等，HARD=困难）
     */
    @TableField("difficulty")
    private String difficulty;

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
