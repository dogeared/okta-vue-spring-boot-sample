package com.example.controller;

import com.example.utils.OAuthUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.controller.BackendController.ACCOUNT_ENDPOINT;
import static com.example.controller.BackendController.BASE_ENDPOINT;
import static com.example.controller.BackendController.HELLO_ENDPOINT;
import static com.example.controller.BackendController.HELLO_TEXT;
import static com.example.controller.BackendController.SECURED_ENDPOINT;
import static com.example.controller.BackendController.SECURED_TEXT;
import static com.example.utils.OAuthUtils.getOauthAuthenticationFor;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BackendController.class)
public class BackendControllerTest {

	@Autowired
	private MockMvc mvc;

	private OidcUser principal;

	private static final String NAME = "Micah Silverman";
	private static final String EMAIL = "micah@afitnerd.com";
	private static final String SUB = "1234567890";
	private static final String AUTHORITY = "user";

	@Before
	public void setUpUser() {
		principal = OAuthUtils.createOidcUser(NAME, EMAIL, SUB, AUTHORITY);
	}

	@Test
	public void hello_api_success() throws Exception {
		mvc.perform(get(BASE_ENDPOINT + HELLO_ENDPOINT))
			.andExpect(status().isOk())
			.andExpect(content().string(HELLO_TEXT));
	}

	@Test
	public void secured_api_unauthenticated_fail() throws Exception {
		mvc.perform(get(BASE_ENDPOINT + SECURED_ENDPOINT))
			.andExpect(status().isFound());
	}

	@Test
	public void secured_api_authenticated_success() throws Exception {
		mvc.perform(get(BASE_ENDPOINT + SECURED_ENDPOINT)
			.with(authentication(getOauthAuthenticationFor(principal))))
			.andExpect(status().isOk())
			.andExpect(content().string(SECURED_TEXT));
	}

	@Test
	public void account_api_success() throws Exception {
		mvc.perform(get(BASE_ENDPOINT + ACCOUNT_ENDPOINT)
			.with(authentication(getOauthAuthenticationFor(principal))))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email", is(EMAIL)))
			.andExpect(jsonPath("$.name", is(NAME)))
			.andExpect(jsonPath("$.sub", is(SUB)));
	}
}
