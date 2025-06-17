package com.physicalcomputing.fingerprintdoorlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FingerprintDoorLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(FingerprintDoorLockApplication.class, args);
	}

}
