package org.proskura.smarthome.sirvice.impl;

import org.proskura.smarthome.domain.DeviceEntity;
import org.proskura.smarthome.domain.UnitEntity;
import org.proskura.smarthome.repository.UnitRepository;
import org.proskura.smarthome.sirvice.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public UnitEntity getUnitByNameAndDevice(String unitName, DeviceEntity deviceEntity) {
        return unitRepository.findByUnitNameAndAndDevice(unitName, deviceEntity)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
    }

    @Override
    public List<UnitEntity> getAllUnitsByDevice(DeviceEntity deviceEntity) {
        return unitRepository.findByDevice(deviceEntity);
    }

    @Override
    public List<UnitEntity> createUnit(List<UnitEntity> unitEntities) {
        return unitRepository.saveAll(unitEntities);
    }
}
