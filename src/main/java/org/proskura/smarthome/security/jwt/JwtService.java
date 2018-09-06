package org.proskura.smarthome.security.jwt;

import io.jsonwebtoken.*;
import org.proskura.smarthome.domain.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Service
public class JwtService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
    public static final String ID = "id";
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
        Jws<Claims> userDetailsParts = parseToken(token);
        Claims claims = userDetailsParts.getBody();
        String id = claims.get(ID, String.class);
        UserRole role = UserRole.valueOf(claims.get("role", String.class));


        DevicePrincipal devicePrincipal = new DevicePrincipal();
        devicePrincipal.setId(id);
        devicePrincipal.setRole(role);
        devicePrincipal.setUnits(Collections.EMPTY_SET);

        return devicePrincipal;
    }


    public Jws<Claims> parseToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            String message = e.getMessage();
            LOGGER.info(message);
            throw new RuntimeException("JWT Expired");
        } catch (JwtException e) {
            String message = e.getMessage();
            LOGGER.info(message);
            throw new RuntimeException("JWT parsing exception");
        }
    }
}
