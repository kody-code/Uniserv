package com.uniserv.ledger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.ledger.entity.LedgerBooks;
import com.uniserv.ledger.mapper.LedgerBooksMapper;
import com.uniserv.ledger.service.ILedgerBooksService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账本表，支持个人/家庭/办公/共享等多种类型，多币种配置 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Service
public class LedgerBooksServiceImpl extends ServiceImpl<LedgerBooksMapper, LedgerBooks> implements ILedgerBooksService {

}
