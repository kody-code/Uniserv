package com.uniserv.ledger.service.impl;

import com.uniserv.ledger.entity.LedgerAccountBooks;
import com.uniserv.ledger.mapper.LedgerAccountBooksMapper;
import com.uniserv.ledger.service.ILedgerAccountBooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户表（用户资产账户） 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Service
public class LedgerAccountBooksServiceImpl extends ServiceImpl<LedgerAccountBooksMapper, LedgerAccountBooks> implements ILedgerAccountBooksService {

}
