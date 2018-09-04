package org.proskura.smarthome.controller;

import org.proskura.smarthome.dto.UsernamePasswordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody UsernamePasswordDto usernamePassword) {


        return ResponseEntity.ok().build();
    }
}
