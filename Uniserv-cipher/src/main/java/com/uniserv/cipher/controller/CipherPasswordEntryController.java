package com.uniserv.cipher.controller;

import com.uniserv.cipher.dto.request.DecryptPasswordRequestDto;
import com.uniserv.cipher.dto.request.PasswordEntryRequestDto;
import com.uniserv.cipher.dto.response.DecryptPasswordResponseDto;
import com.uniserv.cipher.dto.response.PasswordEntryResponseDto;
import com.uniserv.cipher.service.ICipherPasswordEntryService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.utils.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 密码条目表（存储各平台账号密码） 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cipher/password/entry")
@Tag(name = "密码条目接口", description = "密码条目接口")
public class CipherPasswordEntryController {

    /**
     * 密码条目服务
     */
    private final ICipherPasswordEntryService passwordEntryService;


    /**
     * 添加密码条目
     *
     * @param requestDto 请求参数
     * @return 添加结果
     */
    @PostMapping("/add")
    @Operation(summary = "添加密码条目", description = "添加密码条目")
    public ResultUtils<String> addPasswordEntry(@RequestBody @Valid PasswordEntryRequestDto requestDto) throws Exception {
        log.info("添加密码条目");
        if (passwordEntryService.addPasswordEntry(requestDto)) {
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error(ResultCode.BUSINESS_ERROR.getCode(), "添加失败");
    }

    /**
     * 获取所有密码条目
     *
     * @param masterPassword 主密码 请求参数
     * @return 密码条目列表
     */
    @GetMapping("/getAll")
    @Operation(summary = "获取所有密码条目", description = "获取所有密码条目")
    public ResultUtils<List<PasswordEntryResponseDto>> getAllPasswordEntries(@Valid String masterPassword) throws Exception {
        log.info("获取所有密码条目");
        return ResultUtils.success(passwordEntryService.getAllPasswordEntries(masterPassword));
    }

    /**
     * 解密密码
     *
     * @param requestDto 解密请求参数
     * @return 解密结果
     */
    @PostMapping("/decrypt")
    @Operation(summary = "解密密码", description = "解密密码")
    public ResultUtils<DecryptPasswordResponseDto> decryptPassword(@RequestBody @Valid DecryptPasswordRequestDto requestDto) throws Exception {
        log.info("解密密码");
        DecryptPasswordResponseDto decryptPasswordResponseDto = passwordEntryService.decryptPassword(requestDto);
        if (decryptPasswordResponseDto == null) {
            return ResultUtils.error(ResultCode.BUSINESS_ERROR.getCode(), "解密失败");
        }
        return ResultUtils.success(decryptPasswordResponseDto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除密码条目", description = "删除密码条目")
    public ResultUtils<String> deletePasswordEntry(UUID id) {
        log.info("删除密码条目");
        if (passwordEntryService.removeById(id)) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error(ResultCode.BUSINESS_ERROR.getCode(), "删除失败");
    }
}
