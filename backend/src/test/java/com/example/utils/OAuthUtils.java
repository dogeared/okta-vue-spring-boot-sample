package com.example.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OAuthUtils {

    public static OidcUser createOidcUser(String name, String email, String sub, String authority) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", sub);
        attributes.put("name", name);
        attributes.put("email", email);

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(authority));

        OidcIdToken idToken = new OidcIdToken(
            "a token", Instant.now(), Instant.now().plusMillis(20000), attributes
        );
        return new DefaultOidcUser(authorities, idToken, "sub");
    }

    public static Authentication getOauthAuthenticationFor(OidcUser principal) {

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        String authorizedClientRegistrationId = "my-oauth-client";

        return new OAuth2AuthenticationToken(principal, authorities, authorizedClientRegistrationId);
    }
}