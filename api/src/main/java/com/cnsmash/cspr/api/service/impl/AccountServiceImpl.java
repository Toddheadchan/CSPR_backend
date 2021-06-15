package com.cnsmash.cspr.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnsmash.cspr.api.entity.Account;
import com.cnsmash.cspr.api.mapper.AccountMapper;
import com.cnsmash.cspr.api.service.IAccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {
}
