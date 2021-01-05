package com.example.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private List<String> allowedOrigins = List.of("http://localhost:8080");
    private List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE");
    private List<String> corsPaths = List.of("/api/**");

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .cors(corsConfigurer -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(allowedOrigins);
                corsConfiguration.setAllowedMethods(allowedMethods);
                corsConfiguration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                corsPaths.forEach(path -> source.registerCorsConfiguration(path, corsConfiguration));
                corsConfigurer.configurationSource(source);
            })
                .oauth2Client()
            .and()
                .oauth2Login()
            .and()
                .authorizeRequests()
                .antMatchers("/api/hello", "/").permitAll()
                .anyRequest().authenticated()
            .and()
                .csrf().disable(); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!
    }
}
