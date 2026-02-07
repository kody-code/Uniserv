package com.kody.uniserv.app.controller.auth;

import com.kody.uniserv.auth.dto.request.LoginRequestDto;
import com.kody.uniserv.auth.dto.request.RegisterRequestDto;
import com.kody.uniserv.auth.dto.response.LoginResponseDto;
import com.kody.uniserv.auth.dto.response.RegisterResponseDto;
import com.kody.uniserv.auth.service.ISysUsersService;
import com.kody.uniserv.common.result.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 认证控制器
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "认证接口", description = "认证接口")
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
        log.debug("[{}] 登录请求，账号：{}", MDC.get("traceId"), loginRequestDto.getAccount());
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
        log.debug("[{}] 注册请求，用户名：{}", MDC.get("traceId"), registerRequestDto.getUsername());
        return ResultUtils.success(usersService.register(registerRequestDto));
    }

    /**
     * 用户登出
     *
     * @return 登出结果
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出接口")
    public ResultUtils<Void> logout() {
        usersService.logout();
        return ResultUtils.success();
    }
}
