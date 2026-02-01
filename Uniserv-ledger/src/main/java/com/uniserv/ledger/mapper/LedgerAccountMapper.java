package com.uniserv.ledger.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uniserv.ledger.entity.LedgerAccount;

/**
 * <p>
 * 账本下的账户表，记录各类账户信息与余额，支持普通账户与信用账户 Mapper 接口
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
public interface LedgerAccountMapper extends BaseMapper<LedgerAccount> {

}
