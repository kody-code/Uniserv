package com.uniserv.ledger.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uniserv.ledger.entity.LedgerBooks;

/**
 * <p>
 * 账本表，支持个人/家庭/办公/共享等多种类型，多币种配置 Mapper 接口
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
public interface LedgerBooksMapper extends BaseMapper<LedgerBooks> {

}
