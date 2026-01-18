package com.uniserv.cipher.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 密码条目表（存储各平台账号密码） 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password/entry")
@Tag(name = "密码条目接口", description = "密码条目接口")
public class CipherPasswordEntryController {

}
