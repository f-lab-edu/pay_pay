package com.sumin.paypay.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sumin.paypay.entity.Account;
import com.sumin.paypay.model.dto.AccountDto;

@Mapper
public interface AccountMapper {
	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

	@Mapping(source = "id", target = "accountId")
	AccountDto toAccountDto(Account account);

}

