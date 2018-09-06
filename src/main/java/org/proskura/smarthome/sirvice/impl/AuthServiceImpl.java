package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.dto.UsernamePasswordDto;
import org.proskura.smarthome.sirvice.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String generateAccessToken(UsernamePasswordDto usernamePassword) {
        return null;
    }
}
