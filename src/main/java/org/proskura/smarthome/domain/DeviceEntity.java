package org.proskura.smarthome.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "device")
@Data
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "device_id")
    private String deviceId;
    @Column (name = "device_status")
    @Enumerated(STRING)
    private DeviceStatusEnum deviceStatus;
}
