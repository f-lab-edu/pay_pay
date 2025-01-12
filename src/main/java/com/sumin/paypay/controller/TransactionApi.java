package com.sumin.paypay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumin.paypay.model.TransferMapper;
import com.sumin.paypay.model.dto.TransferReqDto;
import com.sumin.paypay.model.dto.TransferResDto;
import com.sumin.paypay.model.request.TransferRequest;
import com.sumin.paypay.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/transaction")
public class TransactionApi {

	private final TransactionService transactionService;

	@PostMapping
	public ResponseEntity<TransferResDto> interTransfer(@RequestBody TransferRequest request) {
		TransferReqDto dto = TransferMapper.INSTANCE.toTransferReqDto(request);
		return ResponseEntity.ok(transactionService.transfer(dto));
	}
}
