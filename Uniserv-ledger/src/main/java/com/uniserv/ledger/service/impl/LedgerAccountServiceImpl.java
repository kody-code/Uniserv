package com.uniserv.ledger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.ledger.entity.LedgerAccount;
import com.uniserv.ledger.mapper.LedgerAccountMapper;
import com.uniserv.ledger.service.ILedgerAccountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账本下的账户表，记录各类账户信息与余额，支持普通账户与信用账户 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Service
public class LedgerAccountServiceImpl extends ServiceImpl<LedgerAccountMapper, LedgerAccount> implements ILedgerAccountService {

}
