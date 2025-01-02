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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sumin.paypay.model.request.AccountCreateRequest;
import com.sumin.paypay.model.response.AccountCreateResponse;
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

		Mockito.when(accountService.create(Mockito.any(AccountCreateRequest.class)))
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

	void getListAccount_Success() throws Exception {

	}
}


