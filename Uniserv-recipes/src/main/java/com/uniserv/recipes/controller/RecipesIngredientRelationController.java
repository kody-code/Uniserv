package com.uniserv.recipes.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜谱食材关系表（多对多关联） 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes/relation")
@Tag(name = "菜谱食材关系表（多对多关联）", description = "菜谱食材关系表（多对多关联）")
public class RecipesIngredientRelationController {

}
