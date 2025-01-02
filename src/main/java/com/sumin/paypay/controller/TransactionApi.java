package com.sumin.paypay.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sumin.paypay.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/transaction")
public class TransactionApi {

	private final TransactionService transactionService;

	@PostMapping
	public ResponseEntity<String> transfer(@RequestParam Long fromAccountId,
		@RequestParam Long toAccountId,
		@RequestParam BigDecimal amount) {
		transactionService.transfer(fromAccountId, toAccountId, amount);
		return ResponseEntity.ok("송금이 완료되었습니다.");
	}
}
