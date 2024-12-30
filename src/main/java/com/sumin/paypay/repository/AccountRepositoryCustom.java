package com.sumin.paypay.repository;

import com.sumin.paypay.entity.Account;

public interface AccountRepositoryCustom {

	Account findByIdWithLock(Long accountId);
}
