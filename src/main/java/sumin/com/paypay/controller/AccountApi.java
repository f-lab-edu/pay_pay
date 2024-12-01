package sumin.com.paypay.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sumin.com.paypay.common.ApiResponseEnvelop;
import sumin.com.paypay.model.request.AccountCreateRequest;
import sumin.com.paypay.model.response.AccountCreateResponse;
import sumin.com.paypay.service.AccountService;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/v1/accounts")
public class AccountApi {

	private final AccountService accountService;

	@PostMapping
	public ApiResponseEnvelop<AccountCreateResponse> create(@RequestBody @Valid AccountCreateRequest request) {
		log.debug("Received request: {}", request);
		return ApiResponseEnvelop.success(accountService.create(request));
	}

	//계좌 목록 조회하기
	//계좌 상세보기
}
