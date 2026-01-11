package com.uniserv.cipher.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.cipher.dto.request.DecryptPasswordRequestDto;
import com.uniserv.cipher.dto.request.PasswordEntryRequestDto;
import com.uniserv.cipher.dto.request.PasswordInfoRequestDto;
import com.uniserv.cipher.dto.response.DecryptPasswordResponseDto;
import com.uniserv.cipher.dto.response.PasswordEntryResponseDto;
import com.uniserv.cipher.entity.PasswordEntry;
import com.uniserv.cipher.mapper.PasswordEntryMapper;
import com.uniserv.cipher.service.IPasswordEntryService;
import com.uniserv.cipher.service.IPasswordInfoService;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.exception.BusinessException;
import com.uniserv.common.utils.PasswordEncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 密码条目表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordEntryServiceImpl extends ServiceImpl<PasswordEntryMapper, PasswordEntry> implements IPasswordEntryService {

    /**
     * 密码加密工具
     */
    private final PasswordEncryptUtil encryptUtil;

    private final IPasswordInfoService passwordInfoService;

    /**
     * 获取当前用户 ID
     *
     * @return 用户 ID
     */
    private String getUserId() {
        // 判断是否登录
        if (StpUtil.isLogin()) {
            // 获取用户 ID
            return StpUtil.getLoginIdAsString();
        } else {
            log.error("请先登录");
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "请先登录");
        }
    }

    private void checkMasterPassword(String masterPassword) throws Exception {
        if (!passwordInfoService.checkMasterPassword(new PasswordInfoRequestDto().setMasterPassword(masterPassword))) {
            throw new BusinessException(ResultCode.AUTH_ERROR.getCode(), "主密码错误");
        }
    }

    /**
     * 添加密码条目
     *
     * @param requestDto 密码条目信息
     * @return 是否添加成功
     */
    @Override
    public boolean addPasswordEntry(PasswordEntryRequestDto requestDto) throws Exception {
        // 获取当前用户 ID
        String userId = getUserId();
        // 验证主密码
        checkMasterPassword(requestDto.getMasterPassword());
        String masterPassword = requestDto.getMasterPassword();
        PasswordEntry passwordEntry = new PasswordEntry();
        passwordEntry.setWebsite(requestDto.getWebsite())
                .setAccount(requestDto.getAccount())
                .setEmail(requestDto.getEmail())
                .setPassword(encryptUtil.encrypt(requestDto.getPassword(), masterPassword))
                .setUrl(requestDto.getUrl())
                .setUserId(UUID.fromString(userId));
        return save(passwordEntry);
    }

    /**
     * 获取所有密码条目
     *
     * @param masterPassword 主密码
     * @return 密码条目列表
     */
    @Override
    public List<PasswordEntryResponseDto> getAllPasswordEntries(String masterPassword) throws Exception {
        // 获取当前用户 ID
        String userId = getUserId();
        // 验证主密码
        checkMasterPassword(masterPassword);
        return baseMapper.selectList(null)
                .stream()
                .filter(passwordEntry -> passwordEntry.getUserId().toString().equals(userId))
                .map(passwordEntry -> new PasswordEntryResponseDto()
                        .setId(passwordEntry.getId())
                        .setWebsite(passwordEntry.getWebsite())
                        .setAccount(passwordEntry.getAccount())
                        .setEmail(passwordEntry.getEmail())
                        .setPassword(passwordEntry.getPassword())
                        .setUrl(passwordEntry.getUrl())).toList();
    }

    /**
     * 解密密码
     *
     * @param requestDto 解密请求参数
     * @return 解密结果
     */
    @Override
    public DecryptPasswordResponseDto decryptPassword(DecryptPasswordRequestDto requestDto) throws Exception {
        // 验证主密码
        checkMasterPassword(requestDto.getMasterPassword());
        // 解密密码
        String decrypt = encryptUtil.decrypt(requestDto.getEncryptedPassword(), requestDto.getMasterPassword());
        return new DecryptPasswordResponseDto()
                .setDecryptedPassword(Base64.getEncoder().encodeToString(decrypt.getBytes()));
    }


}
