package com.physicalcomputing.fingerprintdoorlock.service.deviceService;

import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;

public interface DeviceService {
    Device getDevice(String deviceId);
    Device getDeviceByEmail(String email);
    void CreateDevice(Device device);
}
