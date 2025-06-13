package com.physicalcomputing.fingerprintdoorlock.security;

import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import com.physicalcomputing.fingerprintdoorlock.service.deviceService.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {

    private final DeviceService deviceService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Device device = deviceService.getDeviceByEmail(email);
        if(device == null) {
            throw new UsernameNotFoundException("Device not found");
        }

        return new PrincipalDetails(device); // principalDetails 설정 완료
    }
}
