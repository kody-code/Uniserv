package com.kody.uniserv.app.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kody.uniserv.auth.dto.request.UserPageRequestDto;
import com.kody.uniserv.auth.dto.response.UserInfoDto;
import com.kody.uniserv.auth.service.ISysUsersService;
import com.kody.uniserv.common.dto.PageResponseDto;
import com.kody.uniserv.common.result.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理员用户控制器
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
@Tag(name = "管理员用户接口", description = "管理员用户接口")
public class AdminUserController {

    /**
     * 用户服务
     */
    private final ISysUsersService usersService;

    /**
     * 分页获取所有用户信息
     *
     * @param userPageRequestDto 分页查询参数
     * @return 用户分页信息
     */
    @GetMapping("/list")
    @Operation(summary = "分页获取用户列表", description = "管理员分页获取所有用户信息")
    public ResultUtils<PageResponseDto<UserInfoDto>> getUserList(
            @Valid @Parameter(description = "分页查询参数") UserPageRequestDto userPageRequestDto) {

        log.debug("[{}] 管理员获取用户列表请求，参数：页码={}, 条数={}",
                MDC.get("traceId"), userPageRequestDto.getPageNum(), userPageRequestDto.getPageSize());

        Page<UserInfoDto> pageResult = usersService.getUserPage(userPageRequestDto);
        PageResponseDto<UserInfoDto> responseDto = new PageResponseDto<>(pageResult);

        log.info("[{}] 管理员获取用户列表成功，总数：{}，当前页：{}，每页：{}",
                MDC.get("traceId"), responseDto.getTotal(), responseDto.getPageNum(), responseDto.getPageSize());

        return ResultUtils.success(responseDto);
    }
}