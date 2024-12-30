package com.sumin.paypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumin.paypay.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}