package com.uniserv.ledger.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收入支出类型表（基础分类） 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ledger/types")
@Tag(name = "收入支出类型接口", description = "收入支出类型接口")
public class LedgerExpenseIncomeTypesController {

}
