package com.sumin.paypay.service;

import static com.sumin.paypay.exception.user.UserResultCode.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumin.paypay.entity.Account;
import com.sumin.paypay.exception.CommonException;
import com.sumin.paypay.model.AccountMapper;
import com.sumin.paypay.repository.AccountRepository;
import com.sumin.paypay.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.sumin.paypay.model.dto.AccountDto;
import com.sumin.paypay.model.request.AccountCreateRequest;
import com.sumin.paypay.model.response.AccountCreateResponse;
import com.sumin.paypay.model.response.AccountDetailResponse;
import com.sumin.paypay.model.response.AccountGetResponse;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AccountService {

	private final UserRepository userRepository;
	private final AccountRepository accountRepository;

	@Transactional
	public AccountCreateResponse create(AccountCreateRequest request) {
		validUser(request.getUserId());
		String accountNumber = makeAccountNumber();
		Account account = accountRepository.save(Account.of(request.getUserId(), accountNumber));
		return new AccountCreateResponse(account.getId());
	}

	public List<AccountGetResponse> getList() {
		List<Account> accounts = accountRepository.findAll();
		List<AccountDto> dtos = accounts.stream()
			.sorted(Comparator.comparing(Account::getId))
			.map(AccountMapper.INSTANCE::toAccountDto).toList();
		return dtos.stream().map(AccountGetResponse::to).toList();
	}

	public AccountDetailResponse getDetail(Long accountId, Long userId) {
		AccountDto account = validAndGetAccount(accountId, userId);
		return AccountDetailResponse.of(account);
	}

	public void validUser(Long userId) {
		boolean existed = userRepository.existsById(userId);
		if(!existed) {
			throw new CommonException(INVALID_USER, INVALID_USER.getMessage());
		}
	}

	public AccountDto validAndGetAccount(Long accountId, Long userId) {
		Optional<Account> accountOptional = accountRepository.findByIdAndUserId(accountId, userId);
		if(accountOptional.isEmpty()) {
			throw new CommonException(INVALID_USER, INVALID_USER.getMessage());
		}

		return AccountMapper.INSTANCE.toAccountDto(accountOptional.get());
	}

	private String makeAccountNumber() {
		String accountNumber;

		do {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String datePart = dateFormat.format(new Date());

			int randomPart = (int) (Math.random() * 9000) + 1000;
			accountNumber = datePart + randomPart;

		} while (accountRepository.existsByAccountNumber(accountNumber));

		return accountNumber;
	}

	public void delete(Long accountId, Long userId) {
		Optional<Account> optionalAccount = accountRepository.findByIdAndUserId(accountId, userId);
		if(optionalAccount.isEmpty()) {
			throw new CommonException(INVALID_USER, INVALID_USER.getMessage());
		}

		Account account = optionalAccount.get();
		accountRepository.delete(account);
	}

}
