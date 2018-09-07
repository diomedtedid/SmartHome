package org.proskura.smarthome.controller;

import org.proskura.smarthome.dto.UsernamePasswordDto;
import org.proskura.smarthome.sirvice.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.proskura.smarthome.security.SecurityConstant.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<Void> login (@RequestBody UsernamePasswordDto usernamePassword) {
        String token = authService.generateAccessToken(usernamePassword);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, ACCESS_TOKEN_PREFIX + token).build();
    }

    @PostMapping("/device")
    public ResponseEntity<Void> registerDevice () {
        String jwtToken = authService.generateDeviceToken();
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, JWT_TOKEN_PREFIX + jwtToken).build();
    }
}
