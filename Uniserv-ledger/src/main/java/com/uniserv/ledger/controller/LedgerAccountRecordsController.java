package com.uniserv.ledger.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 账户记录表（收支明细） 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ledger/records")
@Tag(name = "账户记录接口", description = "账户记录接口")
public class LedgerAccountRecordsController {

}
