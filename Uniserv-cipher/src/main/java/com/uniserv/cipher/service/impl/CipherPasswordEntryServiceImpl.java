package com.uniserv.cipher.service.impl;

import com.uniserv.cipher.entity.CipherPasswordEntry;
import com.uniserv.cipher.mapper.CipherPasswordEntryMapper;
import com.uniserv.cipher.service.ICipherPasswordEntryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 密码条目表（存储各平台账号密码） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Service
public class CipherPasswordEntryServiceImpl extends ServiceImpl<CipherPasswordEntryMapper, CipherPasswordEntry> implements ICipherPasswordEntryService {

}
