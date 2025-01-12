package com.sumin.paypay.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.sumin.paypay.model.enums.CurrencyType;
import com.sumin.paypay.type.TransactionType;

@Getter
@NoArgsConstructor
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "update account set deleted = true where id = ?")
@Builder
@Entity
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "from_account_id", nullable = false)
	private Long fromAccountId;

	@Column(name = "to_account_id")
	private Long toAccountId;

	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;

	@Column(name = "currency_type", nullable = false)
	private String currencyType;

	@Column(name = "bank_code")
	private String bankCode;

	@Column(name = "fee", precision = 8, scale = 2)
	private BigDecimal fee;

	@Size(max = 50)
	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type", nullable = false)
	private TransactionType transactionType;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	public static Transaction ofInter(Long fromAccountId, Long toAccountId, BigDecimal amount) {
		return Transaction.builder()
			.fromAccountId(fromAccountId)
			.toAccountId(toAccountId)
			.amount(amount)
			.currencyType(CurrencyType.KRW.name())
			.bankCode("000")
			.transactionType(TransactionType.INTER_TRANSACTION)
			.build();
	}

}