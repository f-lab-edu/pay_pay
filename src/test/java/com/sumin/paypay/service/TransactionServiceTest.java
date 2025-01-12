package com.sumin.paypay.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sumin.paypay.entity.Account;
import com.sumin.paypay.model.dto.TransferReqDto;
import com.sumin.paypay.repository.AccountRepository;


@SpringBootTest
public class TransactionServiceTest {


	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountRepository accountRepository;


	private Long fromAccountId;
	private Long toAccountId;

	@BeforeEach
	void setUp() {
		Account fromAccount = Account.of(10L, "1234");
		Account toAccount = Account.of(11L, "12345");
		fromAccount.changeBalance(BigDecimal.valueOf(100000));
		toAccount.changeBalance(BigDecimal.valueOf(10));
		fromAccount = accountRepository.save(fromAccount);
		toAccount = accountRepository.save(toAccount);

		fromAccountId = fromAccount.getId();
		toAccountId = toAccount.getId();
	}

	@Test
	void 송금_동시성테스트() throws InterruptedException {
		int threadCount = 10;
		BigDecimal transferAmount = new BigDecimal("1");
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.execute(() -> {
				try {
					TransferReqDto transferReqDto = new TransferReqDto(fromAccountId, toAccountId, transferAmount);
					transactionService.transfer(transferReqDto);
				} catch (Exception e) {
					System.err.println("Exception: " + e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow();
		Account toAccount = accountRepository.findById(toAccountId).orElseThrow();

		BigDecimal expectedFromBalance = new BigDecimal("100000").subtract(BigDecimal.valueOf(threadCount));
		BigDecimal expectedToBalance = new BigDecimal("10").add(BigDecimal.valueOf(threadCount));
		System.out.println("expectedFromBalance : " + expectedFromBalance);
		System.out.println("expectedToBalance : " + expectedToBalance);

		assertThat(fromAccount.getBalance()).isEqualByComparingTo(expectedFromBalance);
		assertThat(toAccount.getBalance()).isEqualByComparingTo(expectedToBalance);
	}


	@Test
	@Transactional
	void transfer_잔액부족_테스트() {

		BigDecimal transferAmount = new BigDecimal("10000000");

		TransferReqDto dto = new TransferReqDto(fromAccountId, toAccountId, transferAmount);
		assertThatThrownBy(() -> transactionService.transfer(dto))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("잔액이 부족합니다.");
	}
}
