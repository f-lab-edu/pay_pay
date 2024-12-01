package sumin.com.paypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sumin.com.paypay.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	boolean existsByAccountNumber(String accountNumber);

}