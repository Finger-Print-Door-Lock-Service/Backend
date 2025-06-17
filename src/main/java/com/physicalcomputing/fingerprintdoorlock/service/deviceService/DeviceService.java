package com.physicalcomputing.fingerprintdoorlock.service.deviceService;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;

public interface DeviceService {
    Device getDevice(String deviceId);
    Device getDeviceByEmail(String email);
    Device getDeviceByMacAddress(String macAddress);
    Device getDeviceByDeviceIdForMqtt(int deviceIdForMqtt);
    void registerDevice(ArduinoDTO.DeviceRegisterDTO deviceRegisterDTO);
}
