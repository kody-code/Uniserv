package com.uniserv.recipe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.recipe.entity.RecipeIngredientRelation;
import com.uniserv.recipe.mapper.RecipeIngredientRelationMapper;
import com.uniserv.recipe.service.IRecipeIngredientRelationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜谱食材关系表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Service
public class RecipeIngredientRelationServiceImpl extends ServiceImpl<RecipeIngredientRelationMapper, RecipeIngredientRelation> implements IRecipeIngredientRelationService {

}
