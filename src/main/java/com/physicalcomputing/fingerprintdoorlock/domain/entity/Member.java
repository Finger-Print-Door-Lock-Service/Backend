package com.physicalcomputing.fingerprintdoorlock.domain.entity;

import com.physicalcomputing.fingerprintdoorlock.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    // 이 컬럼은 언제나 memberId + 1이다 => 기기 내에서 MemberId는 1로 시작하기 때문
    @Column(name = "member_id_on_deivce")
    private int memberIdOnDevice;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
}
