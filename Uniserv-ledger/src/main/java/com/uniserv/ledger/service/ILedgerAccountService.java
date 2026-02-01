package com.uniserv.ledger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uniserv.ledger.entity.LedgerAccount;

/**
 * <p>
 * 账本下的账户表，记录各类账户信息与余额，支持普通账户与信用账户 服务类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
public interface ILedgerAccountService extends IService<LedgerAccount> {

}
