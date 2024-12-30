package com.sumin.paypay.docs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.sumin.paypay.exception.user.UserResultCode.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sumin.paypay.entity.Account;
import com.sumin.paypay.exception.CommonException;
import com.sumin.paypay.model.dto.AccountDto;
import com.sumin.paypay.repository.AccountRepository;
import com.sumin.paypay.repository.UserRepository;
import com.sumin.paypay.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private AccountRepository accountRepository;

	@Test
	void validUser_ValidUserId_NoExceptionThrown() {
		// given
		Long userId = 1L;
		when(userRepository.existsById(userId)).thenReturn(true);

		// when & then
		assertDoesNotThrow(() -> accountService.validUser(userId));
		verify(userRepository, times(1)).existsById(userId);
	}

	@Test
	void validUser_InvalidUserId_ThrowsCommonException() {
		// given
		Long userId = 1L;
		when(userRepository.existsById(userId)).thenReturn(false);

		// when & then
		CommonException exception = assertThrows(CommonException.class, () -> accountService.validUser(userId));
		assertEquals(INVALID_USER, exception.getResultCode());
		verify(userRepository, times(1)).existsById(userId);
	}

	@Test
	void validAndGetAccount_ValidAccount_ReturnsAccountDto() {
		// given
		Long accountId = 1L;
		Long userId = 1L;
		Account account = Account.builder()
			.id(accountId)
			.userId(userId)
			.accountNumber("123456")
			.build();
		when(accountRepository.findByIdAndUserId(accountId, userId)).thenReturn(Optional.of(account));

		// when
		AccountDto result = accountService.validAndGetAccount(accountId, userId);

		// then
		assertNotNull(result);
		assertEquals(accountId, result.getAccountId());
		verify(accountRepository, times(1)).findByIdAndUserId(accountId, userId);
	}

	@Test
	void validAndGetAccount_InvalidAccount_ThrowsCommonException() {
		// given
		Long accountId = 1L;
		Long userId = 1L;
		when(accountRepository.findByIdAndUserId(accountId, userId)).thenReturn(Optional.empty());

		// when & then
		CommonException exception = assertThrows(CommonException.class, () -> accountService.validAndGetAccount(accountId, userId));
		assertEquals(INVALID_USER, exception.getResultCode());
		verify(accountRepository, times(1)).findByIdAndUserId(accountId, userId);
	}
}
