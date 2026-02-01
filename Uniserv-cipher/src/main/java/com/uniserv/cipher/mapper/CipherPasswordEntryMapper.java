package com.uniserv.cipher.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uniserv.cipher.entity.CipherPasswordEntry;

import java.util.UUID;

/**
 * <p>
 * 密码条目表（存储各平台账号密码） Mapper 接口
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
public interface CipherPasswordEntryMapper extends BaseMapper<CipherPasswordEntry> {

    int removeTotpById(UUID passwordId);
}
