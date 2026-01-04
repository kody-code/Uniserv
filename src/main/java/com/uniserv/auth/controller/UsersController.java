package com.uniserv.auth.controller;

import com.uniserv.auth.dto.request.LoginRequestDto;
import com.uniserv.auth.dto.response.LoginResponseDto;
import com.uniserv.auth.service.IUsersService;
import com.uniserv.common.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-04
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "用户管理", description = "用户登录、查询、新增等接口")
public class UsersController {

    /**
     * 用户服务
     */
    private final IUsersService UsersService;

    /**
     * 构造函数
     *
     * @param UsersService 用户服务
     */
    public UsersController(IUsersService UsersService) {
        this.UsersService = UsersService;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public Result<LoginResponseDto> login(@Valid LoginRequestDto loginRequestDto) {
        return Result.success(UsersService.login(loginRequestDto));
    }

}
