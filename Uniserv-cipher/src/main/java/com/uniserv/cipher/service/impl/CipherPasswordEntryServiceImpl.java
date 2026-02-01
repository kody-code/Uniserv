package com.uniserv.cipher.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.cipher.dto.request.DecryptPasswordRequestDto;
import com.uniserv.cipher.dto.request.PasswordEntryRequestDto;
import com.uniserv.cipher.dto.request.PasswordInfoRequestDto;
import com.uniserv.cipher.dto.request.TotpEntryRequestDto;
import com.uniserv.cipher.dto.response.DecryptPasswordResponseDto;
import com.uniserv.cipher.dto.response.PasswordEntryResponseDto;
import com.uniserv.cipher.dto.response.TotpEntryResponseDto;
import com.uniserv.cipher.entity.CipherPasswordEntry;
import com.uniserv.cipher.mapper.CipherPasswordEntryMapper;
import com.uniserv.cipher.service.ICipherPasswordEntryService;
import com.uniserv.cipher.service.ICipherPasswordInfoService;
import com.uniserv.cipher.utils.PasswordEncryptUtil;
import com.uniserv.cipher.utils.TotpGeneratorUtil;
import com.uniserv.common.enums.ResultCode;
import com.uniserv.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 密码条目表（存储各平台账号密码） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CipherPasswordEntryServiceImpl extends ServiceImpl<CipherPasswordEntryMapper, CipherPasswordEntry> implements ICipherPasswordEntryService {

    /**
     * 密码加密工具
     */
    private final PasswordEncryptUtil encryptUtil;

    /**
     * 密码信息服务
     */
    private final ICipherPasswordInfoService passwordInfoService;

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
        CipherPasswordEntry passwordEntry = new CipherPasswordEntry();
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
                        .setId(passwordEntry.getPasswordId())
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

    /**
     * 添加 TOTP 令牌
     *
     * @param requestDto TOTP 令牌信息
     * @return 是否添加成功
     */
    @Override
    public TotpEntryResponseDto totpEntry(TotpEntryRequestDto requestDto) {
        // 判断参数是否为空
        if (requestDto == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "参数错误");
        }

        // 获取密码条目 ID
        UUID passwordId = requestDto.getPasswordId();
        // 获取密码条目
        LambdaQueryWrapper<CipherPasswordEntry> query = new LambdaQueryWrapper<>();
        query.eq(CipherPasswordEntry::getPasswordId, passwordId);
        // 更新密码条目
        boolean update = update(
                new CipherPasswordEntry()
                        .setTotpToken(requestDto.getTotpToken())
                , query);
        // 判断是否更新成功
        if (!update) {
            throw new BusinessException(ResultCode.OPERATION_ERROR.getCode(), "添加 TOTP 令牌失败");
        }
        // 生成 TOTP 令牌并返回
        return new TotpEntryResponseDto()
                .setPasswordId(passwordId)
                .setTotp(TotpGeneratorUtil.generateTotpCode(requestDto.getTotpToken()));
    }

    /**
     * 删除 TOTP 令牌
     *
     * @param passwordId 密码条目 ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteTotpEntry(UUID passwordId) {
        // 判断参数是否为空
        if (passwordId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "参数错误");
        }

        // 删除 TOTP 令牌
        return baseMapper.removeTotpById(passwordId) > 0;
    }

    /**
     * 获取 TOTP 值
     *
     * @param passwordId 密码条目 ID
     * @return TOTP 值
     */
    @Override
    public TotpEntryResponseDto getTotpValue(UUID passwordId) {
        // 获取密码条目
        if (passwordId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "参数错误");
        }

        CipherPasswordEntry passwordEntry = getById(passwordId);
        if (passwordEntry == null) {
            throw new BusinessException(ResultCode.NOT_FOUND_ERROR.getCode(), "密码条目不存在");
        }
        if (passwordEntry.getTotpToken() != null) {
            return new TotpEntryResponseDto()
                    .setPasswordId(passwordId)
                    .setTotp(TotpGeneratorUtil.generateTotpCode(passwordEntry.getTotpToken()));
        } else {
            throw new BusinessException(ResultCode.NOT_FOUND_ERROR.getCode(), "TOTP 令牌不存在");
        }
    }
}
