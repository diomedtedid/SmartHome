package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.domain.*;
import org.proskura.smarthome.repository.TokenRepository;
import org.proskura.smarthome.security.jwt.JwtService;
import org.proskura.smarthome.sirvice.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public String createUserToken(UserEntity userEntity, TokenType tokenType) {
        String token = UUID.randomUUID().toString();

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setUser(userEntity);
        tokenEntity.setType(tokenType);
        tokenEntity.setExpirationDate(LocalDateTime.now().plusDays(7));

        tokenRepository.save(tokenEntity);

        return token;
    }

    @Override
    public String createJwtToken(DeviceEntity device) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", device.getDeviceId());
        data.put("role", "DEVICE");

        return jwtService.generateToken(data);
    }
}
