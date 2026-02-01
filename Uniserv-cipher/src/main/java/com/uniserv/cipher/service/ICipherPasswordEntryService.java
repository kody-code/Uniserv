package com.uniserv.cipher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uniserv.cipher.dto.request.DecryptPasswordRequestDto;
import com.uniserv.cipher.dto.request.PasswordEntryRequestDto;
import com.uniserv.cipher.dto.request.TotpEntryRequestDto;
import com.uniserv.cipher.dto.response.DecryptPasswordResponseDto;
import com.uniserv.cipher.dto.response.PasswordEntryResponseDto;
import com.uniserv.cipher.dto.response.TotpEntryResponseDto;
import com.uniserv.cipher.entity.CipherPasswordEntry;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 密码条目表（存储各平台账号密码） 服务类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
public interface ICipherPasswordEntryService extends IService<CipherPasswordEntry> {

    /**
     * 添加密码条目
     *
     * @param requestDto 密码条目信息
     * @return 是否添加成功
     */
    boolean addPasswordEntry(PasswordEntryRequestDto requestDto) throws Exception;

    /**
     * 获取所有密码条目
     *
     * @param masterPassword 主密码
     * @return 密码条目列表
     */
    List<PasswordEntryResponseDto> getAllPasswordEntries(String masterPassword) throws Exception;

    /**
     * 解密密码
     *
     * @param requestDto 解密请求参数
     * @return 解密结果
     */
    DecryptPasswordResponseDto decryptPassword(DecryptPasswordRequestDto requestDto) throws Exception;


    /**
     * 添加 TOTP 令牌
     *
     * @param requestDto TOTP 令牌信息
     * @return 是否添加成功
     */
    TotpEntryResponseDto totpEntry(TotpEntryRequestDto requestDto);

    /**
     * 删除 TOTP 令牌
     *
     * @param passwordId 密码条目 ID
     * @return 是否删除成功
     */
    boolean deleteTotpEntry(UUID passwordId);

    /**
     * 获取 TOTP 值
     *
     * @param passwordId 密码条目 ID
     * @return TOTP 值
     */
    TotpEntryResponseDto getTotpValue(UUID passwordId);

}
