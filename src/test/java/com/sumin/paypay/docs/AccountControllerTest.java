package com.sumin.paypay.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumin.paypay.controller.AccountApi;

import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.sumin.paypay.model.request.AccountCreateRequest;
import com.sumin.paypay.model.request.AccountDeleteRequest;
import com.sumin.paypay.model.response.AccountCreateResponse;
import com.sumin.paypay.model.response.AccountDetailResponse;
import com.sumin.paypay.model.response.AccountGetResponse;
import com.sumin.paypay.service.AccountService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountApi.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	private AccountCreateRequest testAccountCreateRequest;

	@BeforeEach
	void setUp() {
		testAccountCreateRequest = new AccountCreateRequest(1L);
	}

	@Test
	void createAccount_Success() throws Exception {
		AccountCreateResponse response = new AccountCreateResponse(123L);

		Mockito.when(accountService.create(1L))
			.thenReturn(response);

		mockMvc.perform(post("/v1/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(testAccountCreateRequest)))
			.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentation.document("create-account",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("status").description("상태"),
					fieldWithPath("httpStatus").description("HTTP 상태 코드"),
					fieldWithPath("data.id").description("계좌 ID").type(JsonFieldType.NUMBER),
					fieldWithPath("error").description("오류 메시지").optional())
			));
	}

	@Test
	void getListAccount_Success() throws Exception {
		// Given
		List<AccountGetResponse> responseList = List.of(
			new AccountGetResponse(123L, 1L, "1234", BigDecimal.valueOf(10000), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()),
			new AccountGetResponse(124L, 2L, "5678", BigDecimal.valueOf(20000), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now())
		);

		Mockito.when(accountService.getList())
			.thenReturn(responseList);

		// When & Then
		mockMvc.perform(get("/v1/accounts")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentation.document("get-list",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("status").description("상태"),
					fieldWithPath("httpStatus").description("HTTP 상태 코드"),
					fieldWithPath("data.accountId").description("계좌 ID").type(JsonFieldType.NUMBER),
					fieldWithPath("data.userId").description("사용자 ID").type(JsonFieldType.NUMBER),
					fieldWithPath("data.accountNumber").description("계좌 번호"),
					fieldWithPath("data.balance").description("잔액").type(JsonFieldType.NUMBER),
					fieldWithPath("data.transactionAt").description("거래 시간"),
					fieldWithPath("data.createdAt").description("계좌 생성 시간"),
					fieldWithPath("data.updatedAt").description("계좌 업데이트 시간"),
					fieldWithPath("error").description("오류 메시지").optional())
				)
			);
	}

	@Test
	void getDetail_Success() throws Exception {
		// Given
		Long accountId = 123L;
		AccountDetailResponse response = new AccountDetailResponse(accountId, "1234", BigDecimal.valueOf(1000), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());

		Mockito.when(accountService.getDetail(Mockito.anyLong(), Mockito.anyLong()))
			.thenReturn(response);

		// When & Then
		mockMvc.perform(get("/v1/accounts/{accountId}", accountId)
				.param("userId", "User 1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentation.document("get-detail",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("id").description("계좌 ID").type(JsonFieldType.NUMBER),
					fieldWithPath("name").description("계좌 이름"),
					fieldWithPath("status").description("계좌 상태"),
					fieldWithPath("userId").description("사용자 ID")
				)
			));
	}

	@Test
	void delete_Success() throws Exception {
		// Given
		Long accountId = 123L;
		AccountDeleteRequest request = new AccountDeleteRequest(1L);

		// When & Then
		mockMvc.perform(delete("/v1/accounts/{accountId}", accountId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
			.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentation.document("delete-account",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())
			));
	}
}


