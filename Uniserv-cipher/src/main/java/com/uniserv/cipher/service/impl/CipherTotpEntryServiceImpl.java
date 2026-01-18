package com.uniserv.cipher.service.impl;

import com.uniserv.cipher.entity.CipherTotpEntry;
import com.uniserv.cipher.mapper.CipherTotpEntryMapper;
import com.uniserv.cipher.service.ICipherTotpEntryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * TOTP令牌表（双因子认证） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Service
public class CipherTotpEntryServiceImpl extends ServiceImpl<CipherTotpEntryMapper, CipherTotpEntry> implements ICipherTotpEntryService {

}
