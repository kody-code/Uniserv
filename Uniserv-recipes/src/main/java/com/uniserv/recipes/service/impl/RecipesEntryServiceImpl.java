package com.uniserv.recipes.service.impl;

import com.uniserv.recipes.entity.RecipesEntry;
import com.uniserv.recipes.mapper.RecipesEntryMapper;
import com.uniserv.recipes.service.IRecipesEntryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜谱表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Service
public class RecipesEntryServiceImpl extends ServiceImpl<RecipesEntryMapper, RecipesEntry> implements IRecipesEntryService {

}
