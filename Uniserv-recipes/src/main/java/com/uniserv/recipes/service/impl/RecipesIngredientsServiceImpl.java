package com.uniserv.recipes.service.impl;

import com.uniserv.recipes.entity.RecipesIngredients;
import com.uniserv.recipes.mapper.RecipesIngredientsMapper;
import com.uniserv.recipes.service.IRecipesIngredientsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 食材表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Service
public class RecipesIngredientsServiceImpl extends ServiceImpl<RecipesIngredientsMapper, RecipesIngredients> implements IRecipesIngredientsService {

}
