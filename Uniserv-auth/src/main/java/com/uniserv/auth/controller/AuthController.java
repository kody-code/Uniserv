package com.uniserv.auth.controller;

import com.uniserv.auth.dto.request.LoginRequestDto;
import com.uniserv.auth.dto.request.RegisterRequestDto;
import com.uniserv.auth.dto.response.LoginResponseDto;
import com.uniserv.auth.service.IUsersService;
import com.uniserv.common.enums.ResultCode;
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

    /**
     * 用户注册
     *
     * @param registerRequestDto 注册参数
     * @return 注册结果
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public Result<String> register(@Valid RegisterRequestDto registerRequestDto) {
        if (UsersService.register(registerRequestDto)) {
            return Result.success("注册成功");
        }
        return Result.error(ResultCode.BUSINESS_ERROR.getCode(), "注册失败");
    }
}