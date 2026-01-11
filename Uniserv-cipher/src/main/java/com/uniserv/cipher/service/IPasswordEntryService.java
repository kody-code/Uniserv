package com.uniserv.cipher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uniserv.cipher.dto.request.DecryptPasswordRequestDto;
import com.uniserv.cipher.dto.request.PasswordEntryRequestDto;
import com.uniserv.cipher.dto.response.DecryptPasswordResponseDto;
import com.uniserv.cipher.dto.response.PasswordEntryResponseDto;
import com.uniserv.cipher.entity.PasswordEntry;

import java.util.List;

/**
 * <p>
 * 密码条目表 服务类
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
public interface IPasswordEntryService extends IService<PasswordEntry> {

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

}
