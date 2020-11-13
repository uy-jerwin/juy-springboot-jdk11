package juy.web.controller;

import juy.web.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JwtTokenProvider provider;

    @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE } , produces = { MediaType.TEXT_PLAIN_VALUE })
    public String create(@RequestParam("username") final String username) {
        return provider.createToken(username);
    }
}
