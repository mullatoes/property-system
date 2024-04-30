package com.service.property.tenantmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TenantManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenantManagementServiceApplication.class, args);
    }

}
