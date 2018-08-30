package org.proskura.smarthome.sirvice;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface SecurityService {
    Optional<Authentication> getAuthFromToken(String token);
    String getToken (String username, String password);
}
