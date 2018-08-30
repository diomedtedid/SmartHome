package org.proskura.smarthome.controller;

import org.proskura.smarthome.dto.UsernamePasswordDto;
import org.proskura.smarthome.sirvice.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody UsernamePasswordDto usernamePassword) {


        return ResponseEntity.ok().header("auth",
                securityService.getToken(usernamePassword.getUsername(), usernamePassword.getPassword()))
                .build();
    }
}
