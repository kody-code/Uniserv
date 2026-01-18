package com.uniserv.cipher.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 密码管理信息表（存储主密码相关） 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cipher/password/info")
@Tag(name = "密码管理信息", description = "密码管理信息")
public class CipherPasswordInfoController {

}
