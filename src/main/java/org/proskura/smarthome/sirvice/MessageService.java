package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.domain.DeviceEntity;

public interface MessageService {
    String getDeviceAnswer (DeviceEntity deviceEntity);
}
