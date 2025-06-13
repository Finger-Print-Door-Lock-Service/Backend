package com.physicalcomputing.fingerprintdoorlock.security;

import com.physicalcomputing.fingerprintdoorlock.domain.entity.Device;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Getter
public class PrincipalDetails implements UserDetails {

    private final Device device;

    // 기기 등록할때 role을 직접 넣지 않을거기 때문에, 추출할때 수동으로 ROLE_USER을 해준다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
     return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return device.getPassword();
    }

    // 이메일 로그인
    @Override
    public String getUsername() {
        return device.getEmail();
    }
}
