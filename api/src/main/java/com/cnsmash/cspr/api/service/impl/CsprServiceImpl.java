package com.cnsmash.cspr.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnsmash.cspr.api.entity.Account;
import com.cnsmash.cspr.api.entity.Cspr;
import com.cnsmash.cspr.api.mapper.AccountMapper;
import com.cnsmash.cspr.api.mapper.CsprMapper;
import com.cnsmash.cspr.api.service.IAccountService;
import com.cnsmash.cspr.api.service.ICsprService;
import org.springframework.stereotype.Service;

@Service
public class CsprServiceImpl extends ServiceImpl<CsprMapper, Cspr> implements ICsprService {
}
