package com.uniserv.cipher.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.cipher.entity.TotpEntry;
import com.uniserv.cipher.mapper.TotpEntryMapper;
import com.uniserv.cipher.service.ITotpEntryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Totp 表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Service
public class TotpEntryServiceImpl extends ServiceImpl<TotpEntryMapper, TotpEntry> implements ITotpEntryService {

}
