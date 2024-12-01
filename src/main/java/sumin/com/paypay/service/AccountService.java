package sumin.com.paypay.service;

import static sumin.com.paypay.exception.user.UserResultCode.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sumin.com.paypay.entity.Account;
import sumin.com.paypay.exception.CommonException;
import sumin.com.paypay.model.request.AccountCreateRequest;
import sumin.com.paypay.model.response.AccountCreateResponse;
import sumin.com.paypay.repository.AccountRepository;
import sumin.com.paypay.repository.UserRepository;

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

	public void validUser(Long userId) {
		boolean existed = userRepository.existsById(userId);
		if(!existed) {
			throw new CommonException(INVALID_USER, INVALID_USER.getMessage());
		}
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

}
