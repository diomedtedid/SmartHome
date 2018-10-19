package org.proskura.smarthome.security.jwt;

import io.jsonwebtoken.*;
import org.proskura.smarthome.domain.DeviceStatusEnum;
import org.proskura.smarthome.domain.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class JwtService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
    public static final String ID = "deviceId";
    private final byte[] secret;

    public JwtService(@Value("${jwt.token.secret}") byte[] secret) {
        this.secret = secret;
    }

    public String generateToken(Map<String, Object> data) {
        return Jwts.builder()
                .setExpiration(Date.from(LocalDateTime.now().plus(2, ChronoUnit.MONTHS).atZone(ZoneId.systemDefault()).toInstant()))
                .setClaims(data)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @SuppressWarnings("unchecked")
    public DevicePrincipal parseUserDetails(String token) {
        DevicePrincipal devicePrincipal = new DevicePrincipal();

        try {
            Jws<Claims> userDetailsParts = parseToken(token);
            Claims claims = userDetailsParts.getBody();
            String id = claims.get(ID, String.class);
            UserRole role = UserRole.valueOf(claims.get("role", String.class));
            devicePrincipal.setDeviceId(id);
            devicePrincipal.setRole(role);
            devicePrincipal.setDeviceStatus(DeviceStatusEnum.SECURED);
            devicePrincipal.setUnits(Collections.EMPTY_SET);
    } catch (JwtException e) {
            devicePrincipal.setDeviceId("anonymous");
            devicePrincipal.setRole(UserRole.DEVICE);
            devicePrincipal.setDeviceStatus(DeviceStatusEnum.NOT_PERMITTED);
            devicePrincipal.setUnits(Collections.EMPTY_SET);
    }

        return devicePrincipal;
    }


    public Jws<Claims> parseToken(String token) {

            return Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);

    }
}
