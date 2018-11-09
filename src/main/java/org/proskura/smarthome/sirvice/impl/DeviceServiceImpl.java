package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.domain.DeviceEntity;
import org.proskura.smarthome.domain.DeviceStatusEnum;
import org.proskura.smarthome.domain.UnitEntity;
import org.proskura.smarthome.domain.UnitStateEntity;
import org.proskura.smarthome.repository.DeviceRepository;
import org.proskura.smarthome.security.jwt.DevicePrincipal;
import org.proskura.smarthome.sirvice.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private UnitStateService unitStateService;
    @Autowired
    private DeviceRepository deviceRepository;


    @Override
    public String setDataFromDevice(Map<String, Object> data) {
//        DevicePrincipal principal = (DevicePrincipal)authService.getAuthPrincipal();
//        DeviceEntity device;
//        if (principal.getDeviceId().equals("anonymous")) {
//            DeviceEntity deviceFromRequest = getDeviceFromRequest(data);
//            device =  deviceRepository.findByDeviceId(deviceFromRequest.getDeviceId()).orElseGet(() -> deviceRepository.save(deviceFromRequest));
//        } else {
//            device = deviceRepository.findByDeviceId(principal.getDeviceId()).orElseThrow(() -> new RuntimeException("Device from token not present in DB"));
//        }
//
//
//        List<UnitEntity> units = getUnitsFromRequest(data);
//        for (UnitEntity unit : units) {
//            unit.setDevice(device);
//        }

        DeviceEntity requestDevice = getDeviceFromRequest(data);
        DeviceEntity device = deviceRepository.findByDeviceId(requestDevice.getDeviceId()).orElseGet(() -> deviceRepository.save(requestDevice));
        List<UnitEntity> unitsFromRequest = getUnitsFromRequest(data);
        unitsFromRequest.forEach(unit -> unit.setDevice(device));
        List<UnitEntity> units = unitService.getAllUnitsByDevice(device);
        Map<String, UnitEntity> unitsMap = units
                .stream()
                .collect(Collectors.toMap(UnitEntity::getUnitName, unit -> unit));



        List<UnitEntity> newUnits = unitsFromRequest.stream().filter(unit -> !unitsMap.containsKey(unit.getUnitName())).collect(Collectors.toList());

        units.addAll(unitService.createUnit(newUnits));

        unitStateService.saveUnitState(getNewUnitStates(data, units));






        return messageService.getDeviceAnswer(device);
    }

    @Transactional
    public List<UnitEntity> createDeviceWithUnits(Map<String, Object> registrationData) {
        DeviceEntity deviceFromRegistrationData = getDeviceFromRequest(registrationData);
        deviceFromRegistrationData.setDeviceStatus(DeviceStatusEnum.NOT_PERMITTED);

        DeviceEntity deviceEntity = deviceRepository.save(deviceFromRegistrationData);
        List<UnitEntity> unitEntityList = getUnitsFromRequest(registrationData);
        unitEntityList.forEach(unitEntity -> unitEntity.setDevice(deviceEntity));

        return unitService.createUnit(unitEntityList);
    }

    public DeviceEntity getDeviceByDeviceId (String deviceId) {
        return deviceRepository.findByDeviceId(deviceId).orElse(null);
    }

    private DeviceEntity getDeviceFromRequest(Map<String, Object> registrationData) {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setDeviceId(registrationData.get(DEVICE_ID).toString());
        deviceEntity.setDeviceStatus(DeviceStatusEnum.NOT_PERMITTED);
        return deviceEntity;
    }



    private List<UnitEntity> getUnitsFromRequest(Map<String, Object> requestData) {

        return requestData.entrySet().stream().filter(item -> !item.getKey().equals(DEVICE_ID)).map(unit -> {
            UnitEntity unitEntity = new UnitEntity();
            unitEntity.setUnitName(unit.getKey());

            return unitEntity;
        }).collect(Collectors.toList());
    }

    private List<UnitStateEntity> getNewUnitStates (Map<String, Object> data, List<UnitEntity> deviceUnits) {
        return deviceUnits.stream().map(unitEntity -> {
            UnitStateEntity unitState = new UnitStateEntity();
            unitState.setUnit(unitEntity);
            unitState.setStateValue(data.get(unitEntity.getUnitName()).toString());
            unitState.setTime(LocalDateTime.now());
            return unitState;
        }).collect(Collectors.toList());
    }
}
