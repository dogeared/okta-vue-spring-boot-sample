package com.example.configuration;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Bean
    public TomcatContextCustomizer sameSiteCookiesConfig() {
        return context -> {
            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
            context.setCookieProcessor(cookieProcessor);
        };
    }

//    public static class MyRfc6265CookieProcessor extends Rfc6265CookieProcessor {
//        public MyRfc6265CookieProcessor() {
//            super();
//        }
//
//        public String generateHeader(javax.servlet.http.Cookie cookie, HttpServletRequest request) {
//            if (request.getRequestURL().indexOf("ngrok.io") >= 0 && request.getRequestURL().indexOf("/oauth2/authorization/okta") < 0) {
//                cookie.setDomain("ngrok.io");
//            }
//            return super.generateHeader(cookie, request);
//        }
//    }
}