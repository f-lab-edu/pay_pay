package com.sumin.paypay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumin.paypay.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	boolean existsByAccountNumber(String accountNumber);

	Optional<Account> findByIdAndUserId(Long id, Long userId);
}