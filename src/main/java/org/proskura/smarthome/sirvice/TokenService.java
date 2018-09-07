package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.domain.TokenType;
import org.proskura.smarthome.domain.UserEntity;

public interface TokenService {
    String createUserToken (UserEntity userEntity, TokenType tokenType);
    String createJwtToken ();
}
