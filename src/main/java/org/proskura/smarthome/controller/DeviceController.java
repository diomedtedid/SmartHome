package org.proskura.smarthome.controller;

import org.proskura.smarthome.sirvice.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public ResponseEntity<String> postDeviceData (@RequestParam(required = false) Map<String, Object> body) {
        String data = deviceService.updateDeviceData(body);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
