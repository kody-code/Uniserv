package com.uniserv.cipher.controller;

import com.uniserv.cipher.dto.request.PasswordInfoRequestDto;
import com.uniserv.cipher.service.IPasswordInfoService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 密码管理信息 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cipher/password/info")
@Tag(name = "密码管理信息", description = "密码管理信息")
public class PasswordInfoController {

    /**
     * 密码信息服务
     */
    private final IPasswordInfoService passwordInfoService;

    /**
     * 初始化密码信息
     *
     * @param requestDto 请求参数
     * @return 是否成功
     */
    @GetMapping("/init")
    @Operation(summary = "初始化密码信息", description = "初始化密码信息")
    public Result<String> initPasswordInfo(@Valid PasswordInfoRequestDto requestDto) throws Exception {
        if (passwordInfoService.initPasswordInfo(requestDto)) {
            return Result.success("初始化成功");
        }
        return Result.error(ResultCode.BUSINESS_ERROR.getCode(), "初始化失败");
    }

    /**
     * 验证主密码
     *
     * @return 是否成功
     */
    @GetMapping("/check")
    @Operation(summary = "验证主密码", description = "验证主密码")
    public Result<String> checkMasterPassword(@Valid PasswordInfoRequestDto requestDto) throws Exception {
        if (passwordInfoService.checkMasterPassword(requestDto)) {
            return Result.success("验证通过");
        }
        return Result.error(ResultCode.BUSINESS_ERROR.getCode(), "验证失败");
    }

    /**
     * 更新密码信息
     *
     * @param requestDto 密码信息
     * @return 是否成功
     */
    @PostMapping("/update")
    @Operation(summary = "更新密码信息", description = "更新密码信息")
    public Result<String> updatePasswordInfo(@Valid PasswordInfoRequestDto requestDto) throws Exception {
        if (passwordInfoService.updatePasswordInfo(requestDto)) {
            return Result.success("更新成功");
        }
        return Result.error(ResultCode.BUSINESS_ERROR.getCode(), "更新失败");
    }

    /**
     * 删除密码信息
     *
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除密码信息", description = "删除密码信息")
    public Result<String> deletePasswordInfo() {
        if (passwordInfoService.deletePasswordInfo()) {
            return Result.success("删除成功");
        }
        return Result.error(ResultCode.BUSINESS_ERROR.getCode(), "删除失败");
    }
}
