package com.sumin.paypay.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumin.paypay.entity.Account;
import com.sumin.paypay.entity.Transaction;
import com.sumin.paypay.model.TransferMapper;
import com.sumin.paypay.model.dto.TransferReqDto;
import com.sumin.paypay.model.dto.TransferResDto;
import com.sumin.paypay.repository.AccountRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final AccountRepositoryCustom accountQueryRepository;

	@Transactional
	public TransferResDto transfer(TransferReqDto dto) {
		Account fromAccount = accountQueryRepository.findByIdWithLock(dto.getFromAccountId());
		if (fromAccount == null) {
			throw new IllegalArgumentException("출금 계좌를 찾을 수 없습니다.");
		}

		Account toAccount = accountQueryRepository.findByIdWithLock(dto.getToAccountId());
		if (toAccount == null) {
			throw new IllegalArgumentException("입금 계좌를 찾을 수 없습니다.");
		}

		if (fromAccount.getBalance().compareTo(dto.getAmount()) < 0) {
			throw new IllegalArgumentException("잔액이 부족합니다.");
		}

		fromAccount = fromAccount.changeBalance(fromAccount.getBalance().subtract(dto.getAmount()));
		toAccount = toAccount.changeBalance(toAccount.getBalance().add(dto.getAmount()));

		Transaction transaction = Transaction.ofInter(fromAccount.getId(), toAccount.getId(), dto.getAmount());
		return TransferMapper.INSTANCE.toTransferResDto(transaction);
	}
}
