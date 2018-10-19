package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.domain.DeviceEntity;
import org.proskura.smarthome.domain.TokenType;
import org.proskura.smarthome.domain.UnitEntity;
import org.proskura.smarthome.domain.UserEntity;

import java.util.List;
import java.util.Map;

public interface TokenService {
    String createUserToken (UserEntity userEntity, TokenType tokenType);
    String createJwtToken (DeviceEntity device);
}
