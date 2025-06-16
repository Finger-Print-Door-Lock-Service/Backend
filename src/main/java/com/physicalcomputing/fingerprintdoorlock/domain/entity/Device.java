package com.physicalcomputing.fingerprintdoorlock.domain.entity;

import com.physicalcomputing.fingerprintdoorlock.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "device")
@Builder
public class Device extends BaseEntity {

    public static int howManyMembers = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private long deviceId;

    @Column(name = "device_id_for_user")
    private int deviceIdForMqwtt;

    @Column(name = "name")
    private String name;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "device",
                fetch = FetchType.LAZY,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                            CascadeType.REMOVE, CascadeType.REFRESH})
    private List<Member> members;

    public void addMember(Member member) {
        if(members == null) {
            members = new ArrayList<Member>();
        }
        members.add(member);
    }
}
