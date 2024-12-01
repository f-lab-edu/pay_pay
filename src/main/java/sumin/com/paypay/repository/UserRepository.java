package sumin.com.paypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sumin.com.paypay.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}