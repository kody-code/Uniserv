package com.uniserv.recipes.service.impl;

import com.uniserv.recipes.entity.RecipesIngredientRelation;
import com.uniserv.recipes.mapper.RecipesIngredientRelationMapper;
import com.uniserv.recipes.service.IRecipesIngredientRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜谱食材关系表（多对多关联） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Service
public class RecipesIngredientRelationServiceImpl extends ServiceImpl<RecipesIngredientRelationMapper, RecipesIngredientRelation> implements IRecipesIngredientRelationService {

}
