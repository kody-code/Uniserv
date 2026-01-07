package com.uniserv.auth.controller;

import com.uniserv.auth.dot.request.LoginRequestDto;
import com.uniserv.auth.dot.response.LoginResponseDto;
import com.uniserv.auth.service.IUsersService;
import com.uniserv.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "用户管理", description = "用户登录、查询、新增等接口")
public class AuthController {


    /**
     * 用户服务
     */
    private final IUsersService UsersService;


    /**
     * 用户登录
     *
     * @param loginRequestDto 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public Result<LoginResponseDto> login(@Valid LoginRequestDto loginRequestDto) {
        return Result.success(UsersService.login(loginRequestDto));
    }
}