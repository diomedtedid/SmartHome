package org.proskura.smarthome.controller;

import org.proskura.smarthome.sirvice.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/")
public class SimpleRestController {
    @Autowired
    private AuthService uathService;

    @GetMapping()
    public String helloWorld () {
        return uathService.getAuthPrincipal().toString();
    }
}
