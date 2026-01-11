package com.uniserv.ledger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniserv.ledger.entity.AccountBooks;
import com.uniserv.ledger.mapper.AccountBooksMapper;
import com.uniserv.ledger.service.IAccountBooksService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Service
public class AccountBooksServiceImpl extends ServiceImpl<AccountBooksMapper, AccountBooks> implements IAccountBooksService {

}
