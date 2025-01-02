package com.sumin.paypay.repository;

import jakarta.persistence.LockModeType;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sumin.paypay.entity.Account;
import com.sumin.paypay.entity.QAccount;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;


	public Account findByIdWithLock(Long accountId) {
		QAccount account = QAccount.account;

		// 비관적 락 적용
		return jpaQueryFactory
			.from(account)
			.where(account.id.eq(accountId))
			.select(account)
			.setLockMode(LockModeType.PESSIMISTIC_WRITE)
			.fetchFirst();
	}
}
