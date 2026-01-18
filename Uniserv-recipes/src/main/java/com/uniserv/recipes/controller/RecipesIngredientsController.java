package com.uniserv.recipes.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 食材表 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes/ingredients")
@Tag(name = "食材表", description = "食材表")
public class RecipesIngredientsController {

}
