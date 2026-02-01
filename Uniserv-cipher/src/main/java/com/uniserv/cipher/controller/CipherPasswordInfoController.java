package com.uniserv.cipher.controller;

import com.uniserv.cipher.dto.request.PasswordInfoRequestDto;
import com.uniserv.cipher.service.ICipherPasswordInfoService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 密码管理信息表（存储主密码相关） 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cipher/password/info")
@Tag(name = "密码管理信息", description = "密码管理信息")
public class CipherPasswordInfoController {

    /**
     * 密码信息服务
     */
    private final ICipherPasswordInfoService passwordInfoService;

    /**
     * 初始化密码信息
     *
     * @param requestDto 请求参数
     * @return 是否成功
     */
    @GetMapping("/init")
    @Operation(summary = "初始化密码信息", description = "初始化密码信息")
    public ResultUtils<String> initPasswordInfo(@Valid PasswordInfoRequestDto requestDto) throws Exception {
        if (passwordInfoService.initPasswordInfo(requestDto)) {
            return ResultUtils.success("初始化成功");
        }
        return ResultUtils.error(ResultCode.BUSINESS_ERROR.getCode(), "初始化失败");
    }

    /**
     * 验证主密码
     *
     * @return 是否成功
     */
    @GetMapping("/check")
    @Operation(summary = "验证主密码", description = "验证主密码")
    public ResultUtils<String> checkMasterPassword(@Valid PasswordInfoRequestDto requestDto) throws Exception {
        if (passwordInfoService.checkMasterPassword(requestDto)) {
            return ResultUtils.success("验证通过");
        }
        return ResultUtils.error(ResultCode.BUSINESS_ERROR.getCode(), "验证失败");
    }

    /**
     * 更新密码信息
     *
     * @param requestDto 密码信息
     * @return 是否成功
     */
    @PostMapping("/update")
    @Operation(summary = "更新密码信息", description = "更新密码信息")
    public ResultUtils<String> updatePasswordInfo(@Valid PasswordInfoRequestDto requestDto) throws Exception {
        if (passwordInfoService.updatePasswordInfo(requestDto)) {
            return ResultUtils.success("更新成功");
        }
        return ResultUtils.error(ResultCode.BUSINESS_ERROR.getCode(), "更新失败");
    }

    /**
     * 删除密码信息
     *
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除密码信息", description = "删除密码信息")
    public ResultUtils<String> deletePasswordInfo() {
        if (passwordInfoService.deletePasswordInfo()) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error(ResultCode.BUSINESS_ERROR.getCode(), "删除失败");
    }
}
