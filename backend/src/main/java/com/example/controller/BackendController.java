package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(BackendController.BASE_ENDPOINT)
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    public static final String BASE_ENDPOINT = "/api";
    public static final String HELLO_ENDPOINT = "/hello";
    public static final String ACCOUNT_ENDPOINT = "/account";
    public static final String SECURED_ENDPOINT = "/secured";

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @GetMapping(HELLO_ENDPOINT)
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @GetMapping(ACCOUNT_ENDPOINT)
    public Map<String, Object> getAccount(@AuthenticationPrincipal OidcUser user) {
        return user.getClaims();
    }

    @GetMapping(SECURED_ENDPOINT)
    public @ResponseBody String getSecured() {
        LOG.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }
}
