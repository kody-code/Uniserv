package com.uniserv.cipher.controller;

import com.uniserv.cipher.dto.request.TotpEntryRequestDto;
import com.uniserv.cipher.dto.response.TotpEntryResponseDto;
import com.uniserv.cipher.service.ICipherPasswordEntryService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <p>
 * TOTP令牌表（双因子认证） 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-02-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cipher/totp")
@Tag(name = "TOTP 令牌", description = "TOTP 令牌")
public class CipherTotpEntryController {


    /**
     * 密码条目服务
     */
    private final ICipherPasswordEntryService passwordEntryService;

    /**
     * 获取 TOTP 令牌
     *
     * @param passwordId 获取 TOTP 令牌请求参数密码ID
     * @return 获取 TOTP 令牌结果
     */
    @PostMapping("/get")
    @Operation(summary = "获取 TOTP 令牌", description = "获取 TOTP 令牌")
    public ResultUtils<TotpEntryResponseDto> getTotpEntry(UUID passwordId) {
        TotpEntryResponseDto responseDto = passwordEntryService.getTotpValue(passwordId);
        return ResultUtils.success(responseDto);
    }

    /**
     * 添加 TOTP 令牌
     *
     * @param requestDto 添加 TOTP 令牌请求参数
     * @return 添加 TOTP 令牌结果
     */
    @PostMapping("/add")
    @Operation(summary = "添加 TOTP 令牌", description = "添加 TOTP 令牌")
    public ResultUtils<TotpEntryResponseDto> addTotpEntry(TotpEntryRequestDto requestDto) {
        TotpEntryResponseDto responseDto = passwordEntryService.totpEntry(requestDto);
        return ResultUtils.success(responseDto);
    }

    /**
     * 更新 TOTP 令牌
     *
     * @param requestDto 更新 TOTP 令牌请求参数
     * @return 更新 TOTP 令牌结果
     */
    @PostMapping("/update")
    @Operation(summary = "更新 TOTP 令牌", description = "更新 TOTP 令牌")
    public ResultUtils<TotpEntryResponseDto> updateTotpEntry(TotpEntryRequestDto requestDto) {
        TotpEntryResponseDto responseDto = passwordEntryService.totpEntry(requestDto);
        return ResultUtils.success(responseDto);
    }

    /**
     * 删除 TOTP 令牌
     *
     * @param passwordId 删除 TOTP 令牌请求参数密码ID
     * @return 删除 TOTP 令牌结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除 TOTP 令牌", description = "删除 TOTP 令牌")
    public ResultUtils<String> deleteTotpEntry(UUID passwordId) {
        if (passwordEntryService.deleteTotpEntry(passwordId)) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error(ResultCode.OPERATION_ERROR.getCode(), "删除失败");
    }
}
