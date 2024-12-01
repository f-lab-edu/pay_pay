package sumin.com.paypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sumin.com.paypay.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}