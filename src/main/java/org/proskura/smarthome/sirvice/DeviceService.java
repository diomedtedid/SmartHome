package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.domain.UnitEntity;

import java.util.List;
import java.util.Map;

public interface DeviceService {
    String updateDeviceData(Map<String, Object> data);
    List<UnitEntity> createDeviceWithUnits (Map<String, Object> registrationData);
}
