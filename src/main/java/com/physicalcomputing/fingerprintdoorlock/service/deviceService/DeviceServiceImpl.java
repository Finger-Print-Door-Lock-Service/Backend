package com.physicalcomputing.fingerprintdoorlock.service.deviceService;

import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;


    @Override
    public Device getDevice(String deviceId) {
        return null;
    }

    @Override
    public Device getDeviceByEmail(String email) {
        Device device = deviceRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));
        return device;
    }

    @Override
    public void CreateDevice(Device device) {

    }
}
