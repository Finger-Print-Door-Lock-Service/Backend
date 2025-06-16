package com.physicalcomputing.fingerprintdoorlock.service.deviceService;

import com.physicalcomputing.fingerprintdoorlock.domain.dto.ArduinoDTO;
import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;
    private final PasswordEncoder passwordEncoder;

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
    public Device getDeviceByMacAddress(String macAddress) {
        Device device = deviceRepository.findByMacAddress(macAddress)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));
        return device;
    }

    @Override
    public void registerDevice(ArduinoDTO.DeviceRegisterDTO deviceRegisterDTO) {
        String macAddress = deviceRegisterDTO.getMacAddress();
        String name = deviceRegisterDTO.getName();
        String email = deviceRegisterDTO.getEmail();
        String password = passwordEncoder.encode(deviceRegisterDTO.getPassword());
        int deviceIdForMqtt = deviceRegisterDTO.getDeviceIdForMqtt();

        Device device = Device.builder()
                .name(name)
                .macAddress(macAddress)
                .email(email)
                .password(password)
                .deviceIdForMqwtt(deviceIdForMqtt)
                .build();

        deviceRepository.save(device);
    }
}
