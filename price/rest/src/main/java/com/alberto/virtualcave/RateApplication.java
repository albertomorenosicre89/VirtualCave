package com.alberto.virtualcave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.alberto.virtualcave.dao"})
@EntityScan(basePackages = { "com.alberto.virtualcave.entities" })
@SpringBootApplication(scanBasePackages = "com.alberto.virtualcave")
@EnableFeignClients(basePackages = "com.alberto.virtualcave.services")
public class RateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RateApplication.class, args);
	}

}
