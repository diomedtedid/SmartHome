package org.proskura.smarthome.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.proskura.smarthome.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityConfigTest {
    @Autowired
    JwtService jwtService;

    @Test
    public void someTest() {
        Map<String, Object> data = new HashMap<>();
        data.put("deviceId", "someId");
        data.put("role", "DEVICE");
        String token = jwtService.generateToken(data);
        System.out.println();
        System.out.println("============================");
        System.out.println(token);
        System.out.println("============================");
        System.out.println();

        System.out.println();
        System.out.println("============================");
        System.out.println(jwtService.parseToken(token));
        System.out.println("============================");
        System.out.println();
    }

}