package com.uniserv.auth.controller;

import com.uniserv.auth.dto.request.LoginRequestDto;
import com.uniserv.auth.dto.request.RegisterRequestDto;
import com.uniserv.auth.dto.response.LoginResponseDto;
import com.uniserv.auth.dto.response.RegisterResponseDto;
import com.uniserv.auth.service.ISysUsersService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author kody
 * @since 2026-01-26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "用户管理", description = "用户登录、查询、新增等接口")
public class AuthController {

    /**
     * 用户服务
     */
    private final ISysUsersService usersService;


    /**
     * 用户登录
     *
     * @param loginRequestDto 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public ResultUtils<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResultUtils.success(usersService.login(loginRequestDto));
    }

    /**
     * 用户注册
     *
     * @param registerRequestDto 注册参数
     * @return 注册结果
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public ResultUtils<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        return ResultUtils.success(usersService.register(registerRequestDto));
    }

    @GetMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出接口")
    public ResultUtils<Void> logout() {
        if (usersService.logout()) {
            return ResultUtils.success();
        }
        return ResultUtils.error(ResultCode.AUTH_ERROR.getCode(), "登出失败");
    }
}