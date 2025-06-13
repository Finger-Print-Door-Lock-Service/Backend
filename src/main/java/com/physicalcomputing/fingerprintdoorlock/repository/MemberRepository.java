package com.physicalcomputing.fingerprintdoorlock.repository;

import com.physicalcomputing.fingerprintdoorlock.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
