package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class VueForwardController {

    private static final Logger LOG = LoggerFactory.getLogger(VueForwardController.class);

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @GetMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        LOG.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/";
    }

//    @GetMapping("/")
//    public void redir(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        LOG.info("URL entered directly into the Browser, so we need to redirect...");
//
//        Cookie c = request.getCookies()[0];
//        c.setSecure(true);
//        c.setDomain("ngrok.io");
//        response.addCookie(c);
//        //return "forward:/";
//        response.sendRedirect("https://micah-local.ngrok.io");
//    }
}
