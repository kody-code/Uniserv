package com.uniserv.auth.controller;

import com.uniserv.auth.entity.SysUsers;
import com.uniserv.auth.service.ISysUsersService;
import com.uniserv.common.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
@Tag(name = "管理员管理", description = "管理员管理接口")
public class AdminController {
    /**
     * 用户服务
     */
    private final ISysUsersService usersService;


    /**
     * 获取所有用户
     *
     * @return 所有用户列表
     */
    @GetMapping("/getAllUsers")
    @Operation(summary = "获取所有用户")
    public ResultUtils<List<SysUsers>> getAllUsers() {
        return ResultUtils.success(usersService.getAllUsers());
    }

}
