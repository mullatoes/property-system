package com.service.property.paymentsbillingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaymentsBillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsBillingServiceApplication.class, args);
    }

}
