package com.sumin.paypay.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.sumin.paypay.entity.Transaction;
import com.sumin.paypay.model.dto.TransferReqDto;
import com.sumin.paypay.model.dto.TransferResDto;
import com.sumin.paypay.model.request.TransferRequest;

@Mapper
public interface TransferMapper {

	TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);

	TransferReqDto toTransferReqDto(TransferRequest request);

	TransferResDto toTransferResDto(Transaction transaction);
}
