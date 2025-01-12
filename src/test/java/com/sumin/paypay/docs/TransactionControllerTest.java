package com.sumin.paypay.docs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.sumin.paypay.controller.TransactionApi;
import com.sumin.paypay.model.dto.TransferReqDto;
import com.sumin.paypay.model.dto.TransferResDto;
import com.sumin.paypay.model.request.TransferRequest;
import com.sumin.paypay.service.TransactionService;
import com.sumin.paypay.type.TransactionType;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionApi.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;

	@Test
	void transaction_Success() throws Exception {
		// Given
		TransferRequest transferRequest = new TransferRequest(1L, 2L, BigDecimal.valueOf(500));
		TransferResDto transferResDto = new TransferResDto(1L, 1L, 2L, BigDecimal.valueOf(500), "KRW", null, null, TransactionType.INTER_TRANSACTION, LocalDateTime.now());

		// TransferReqDto로 변환된 값
		TransferReqDto transferReqDto = new TransferReqDto(1L, 2L, BigDecimal.valueOf(500));

		// Mocking the service call
		Mockito.when(transactionService.transfer(Mockito.any(TransferReqDto.class)))
			.thenReturn(transferResDto);

		// When & Then
		mockMvc.perform(post("/v1/transfer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(transferRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("Transfer successful"))
			.andExpect(jsonPath("$.amount").value(500.00))
			.andDo(MockMvcRestDocumentation.document("inter-transfer",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("fromAccountId").description("송금 계좌 ID"),
					fieldWithPath("toAccountId").description("수신 계좌 ID"),
					fieldWithPath("amount").description("송금 금액")
				),
				responseFields(
					fieldWithPath("id").description("송금 고유 ID").type(JsonFieldType.NUMBER),
					fieldWithPath("fromAccountId").description("송금 계좌 ID").type(JsonFieldType.NUMBER),
					fieldWithPath("toAccountId").description("수취 계좌 ID").type(JsonFieldType.NUMBER),
					fieldWithPath("amount").description("송금된 금액").type(JsonFieldType.NUMBER),
					fieldWithPath("currencyType").description("통화").type(JsonFieldType.STRING),
					fieldWithPath("bankCode").description("은행 코드").type(JsonFieldType.STRING),
					fieldWithPath("fee").description("수수료").type(JsonFieldType.STRING),
					fieldWithPath("transactionType").description("송금 종류").type(JsonFieldType.STRING),
					fieldWithPath("createdAt").description("송금 시간").type(JsonFieldType.STRING))
				)
			);

	}
}
