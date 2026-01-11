package com.uniserv.recipe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.recipe.entity.Ingredients;
import com.uniserv.recipe.mapper.IngredientsMapper;
import com.uniserv.recipe.service.IIngredientsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 食材表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Service
public class IngredientsServiceImpl extends ServiceImpl<IngredientsMapper, Ingredients> implements IIngredientsService {

}
