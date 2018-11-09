package org.proskura.smarthome.sirvice;

import org.proskura.smarthome.domain.DeviceEntity;
import org.proskura.smarthome.domain.UnitEntity;

import java.util.List;
import java.util.Map;

public interface DeviceService {
    String setDataFromDevice(Map<String, Object> data);
    DeviceEntity getDeviceByDeviceId (String deviceId);
}
