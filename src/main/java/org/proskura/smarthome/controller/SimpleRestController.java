package org.proskura.smarthome.controller;

import org.proskura.smarthome.security.Principal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class SimpleRestController {

    @GetMapping()
    public String helloWorld () {
        List<String> list = new ArrayList<>();
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        list.add(principal.toString());
        return list.toString();
    }
}
