package com.sumin.paypay.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.sumin.paypay.model.request.AccountCreateRequest;
import com.sumin.paypay.model.request.AccountDeleteRequest;
import com.sumin.paypay.model.request.AccountDetailRequest;
import com.sumin.paypay.model.response.AccountCreateResponse;
import com.sumin.paypay.model.response.AccountDetailResponse;
import com.sumin.paypay.model.response.AccountGetResponse;
import com.sumin.paypay.service.AccountService;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/accounts")
public class AccountApi {

	private final AccountService accountService;

	@PostMapping
	public AccountCreateResponse create(@RequestBody @Valid AccountCreateRequest request) {
		log.debug("Received request: {}", request);
		return accountService.create(request);
	}

	//계좌 목록 조회하기
	@GetMapping
	public List<AccountGetResponse> getList() {
		return accountService.getList();
	}

	//계좌 상세보기
	@GetMapping("/{accountId}")
	public AccountDetailResponse getDetail(
		@PathVariable @NotNull final Long accountId,
		@ModelAttribute AccountDetailRequest request
	) {
		return accountService.getDetail(accountId, request.getUserId());
	}

	//계좌 삭제하기
	@DeleteMapping("/{accountId}")
	public void delete(
		@PathVariable @NotNull final Long accountId,
		@ModelAttribute AccountDeleteRequest request
	) {
		accountService.delete(accountId, request.getUserId());
	}

}
