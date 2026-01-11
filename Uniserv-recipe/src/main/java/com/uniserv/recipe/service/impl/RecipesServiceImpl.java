package com.uniserv.recipe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.recipe.entity.Recipes;
import com.uniserv.recipe.mapper.RecipesMapper;
import com.uniserv.recipe.service.IRecipesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜谱表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Service
public class RecipesServiceImpl extends ServiceImpl<RecipesMapper, Recipes> implements IRecipesService {

}
