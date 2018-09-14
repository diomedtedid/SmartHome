package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.domain.DeviceEntity;
import org.proskura.smarthome.domain.UnitEntity;
import org.proskura.smarthome.domain.UnitStateEntity;
import org.proskura.smarthome.repository.DeviceRepository;
import org.proskura.smarthome.security.jwt.DevicePrincipal;
import org.proskura.smarthome.sirvice.AuthService;
import org.proskura.smarthome.sirvice.DeviceService;
import org.proskura.smarthome.sirvice.MessageService;
import org.proskura.smarthome.sirvice.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService{
    private static final String DEVICE_ID = "deviceId";
    @Autowired
    private AuthService authService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private DeviceRepository deviceRepository;


    @Override
    public String updateDeviceData(Map<String, Object> data) {
        DevicePrincipal principal = (DevicePrincipal)authService.getAuthPrincipal();
        DeviceEntity device = deviceRepository.findByDeviceId(principal.getDeviceId()).get();
        Set<UnitStateEntity> unitStateSet = parseDeviceRequest(data, device);

        return messageService.getDeviceAnswer(device);
    }

    @Override
    @Transactional
    public List<UnitEntity> createDeviceWithUnits(Map<String, Object> registrationData) {
        DeviceEntity deviceEntity = deviceRepository.save(getDeviceFromRegistrationData (registrationData));
        List<UnitEntity> unitEntityList = getUnitsFromRegistrationData(registrationData);
        unitEntityList.forEach(unitEntity -> unitEntity.setDevice(deviceEntity));

        return unitService.createUnit(unitEntityList);
    }

    private DeviceEntity getDeviceFromRegistrationData (Map<String, Object> registrationData) {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setDeviceId(registrationData.get(DEVICE_ID).toString());
        return deviceEntity;
    }



    private List<UnitEntity> getUnitsFromRegistrationData (Map<String, Object> registrationData) {

        return registrationData.entrySet().stream().filter(item -> !item.getKey().equals(DEVICE_ID)).map(unit -> {
            UnitEntity unitEntity = new UnitEntity();
            unitEntity.setUnitName(unit.getKey());

            return unitEntity;
        }).collect(Collectors.toList());
    }

    private Set<UnitStateEntity> parseDeviceRequest(Map<String, Object> data, DeviceEntity device) {

         return data.entrySet().stream().map(item -> {
                UnitEntity unit = unitService.getUnitByNameAndDevice(item.getKey(), device);

                UnitStateEntity unitState = new UnitStateEntity();
                unitState.setUnit(unit);
                unitState.setValue(item.getValue().toString());
                unitState.setTime(LocalDateTime.now());

                return unitState;
            }).collect(Collectors.toSet());
    }
}
