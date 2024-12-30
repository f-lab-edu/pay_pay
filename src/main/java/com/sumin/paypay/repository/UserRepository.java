package com.sumin.paypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumin.paypay.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}