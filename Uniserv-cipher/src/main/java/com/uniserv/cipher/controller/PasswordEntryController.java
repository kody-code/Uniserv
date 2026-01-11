package com.uniserv.cipher.controller;

import com.uniserv.cipher.dto.request.DecryptPasswordRequestDto;
import com.uniserv.cipher.dto.request.PasswordEntryRequestDto;
import com.uniserv.cipher.dto.response.DecryptPasswordResponseDto;
import com.uniserv.cipher.dto.response.PasswordEntryResponseDto;
import com.uniserv.cipher.service.IPasswordEntryService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.result.Result;
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
 * 密码条目表 前端控制器
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password/entry")
@Tag(name = "密码条目接口", description = "密码条目接口")
public class PasswordEntryController {

    /**
     * 密码条目服务
     */
    private final IPasswordEntryService passwordEntryService;


    /**
     * 添加密码条目
     *
     * @param requestDto 请求参数
     * @return 添加结果
     */
    @PostMapping("/add")
    @Operation(summary = "添加密码条目", description = "添加密码条目")
    public Result<String> addPasswordEntry(@RequestBody @Valid PasswordEntryRequestDto requestDto) throws Exception {
        log.info("添加密码条目");
        if (passwordEntryService.addPasswordEntry(requestDto)) {
            return Result.success("添加成功");
        }
        return Result.error(ResultCode.BUSINESS_ERROR.getCode(), "添加失败");
    }

    /**
     * 获取所有密码条目
     *
     * @param masterPassword 主密码 请求参数
     * @return 密码条目列表
     */
    @GetMapping("/getAll")
    @Operation(summary = "获取所有密码条目", description = "获取所有密码条目")
    public Result<List<PasswordEntryResponseDto>> getAllPasswordEntries(@RequestBody @Valid String masterPassword) throws Exception {
        log.info("获取所有密码条目");
        return Result.success(passwordEntryService.getAllPasswordEntries(masterPassword));
    }

    /**
     * 解密密码
     *
     * @param requestDto 解密请求参数
     * @return 解密结果
     */
    @PostMapping("/decrypt")
    @Operation(summary = "解密密码", description = "解密密码")
    public Result<DecryptPasswordResponseDto> decryptPassword(@RequestBody @Valid DecryptPasswordRequestDto requestDto) throws Exception {
        log.info("解密密码");
        DecryptPasswordResponseDto decryptPasswordResponseDto = passwordEntryService.decryptPassword(requestDto);
        if (decryptPasswordResponseDto == null) {
            return Result.error(ResultCode.BUSINESS_ERROR.getCode(), "解密失败");
        }
        return Result.success(decryptPasswordResponseDto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除密码条目", description = "删除密码条目")
    public Result<String> deletePasswordEntry(@RequestBody @Valid UUID id) {
        log.info("删除密码条目");
        if (passwordEntryService.removeById(id)) {
            return Result.success("删除成功");
        }
        return Result.error(ResultCode.BUSINESS_ERROR.getCode(), "删除失败");
    }
}
