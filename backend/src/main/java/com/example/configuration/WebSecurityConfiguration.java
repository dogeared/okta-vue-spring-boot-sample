package com.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Value("#{ @environment['security.allowed-origins'] }")
    private String[] allowedOrigins;

    @Value("#{ @environment['security.allowed-methods'] }")
    private String[] allowedMethods;

    @Value("#{ @environment['security.cors-paths'] }")
    private List<String> corsPaths;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .cors()
            .and()
                .oauth2Client()
            .and()
                .oauth2Login()
            .and()
                .logout()
            .and()
                .authorizeRequests()
                .antMatchers("/api/hello", "/").permitAll()
                .anyRequest().authenticated()
            .and()
                .csrf().disable();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        corsPaths.forEach(path -> {
            registry.addMapping(path)
                .allowedMethods(allowedMethods)
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true);
        });
    }
}
