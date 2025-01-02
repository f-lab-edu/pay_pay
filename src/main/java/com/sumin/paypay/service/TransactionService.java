package com.sumin.paypay.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumin.paypay.entity.Account;
import com.sumin.paypay.repository.AccountRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final AccountRepositoryCustom accountQueryRepository;

	@Transactional
	public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
		Account fromAccount = accountQueryRepository.findByIdWithLock(fromAccountId);
		if (fromAccount == null) {
			throw new IllegalArgumentException("출금 계좌를 찾을 수 없습니다.");
		}

		Account toAccount = accountQueryRepository.findByIdWithLock(toAccountId);
		if (toAccount == null) {
			throw new IllegalArgumentException("입금 계좌를 찾을 수 없습니다.");
		}

		if (fromAccount.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("잔액이 부족합니다.");
		}

		fromAccount.changeBalance(fromAccount.getBalance().subtract(amount));
		toAccount.changeBalance(toAccount.getBalance().add(amount));

		//TODO 저장

	}
}
