package com.m.finfrau;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m.finfrau.requests.ExpenseRequest;
import com.m.finfrau.requests.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = FinfrauApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ExpenseIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private String token;

	@BeforeEach
	void setUp() throws Exception {

		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setUsername("testuser322");
		registerRequest.setPassword("password");

		mockMvc.perform(post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(registerRequest)))
				.andExpect(status().isOk());


		var result = mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(registerRequest)))
				.andExpect(status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		token = response.substring(10, response.length()-2);
		System.out.println(response.substring(10, response.length()-2));
	}

	@Test
	void testAddAndFetchExpense() throws Exception {

		ExpenseRequest expenseRequest = new ExpenseRequest();
		expenseRequest.setAmount(new BigDecimal("123.45"));
		expenseRequest.setDescription("Test Expense");
		expenseRequest.setCategory("Продукты");
		expenseRequest.setCurrency("RUB");
		expenseRequest.setExpenseDate(LocalDate.now());

		mockMvc.perform(post("/expenses")
						.header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(expenseRequest)))
				.andExpect(status().isOk());


		mockMvc.perform(get("/expenses")
						.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].description").value("Test Expense"));
	}
}

