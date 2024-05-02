package com.service.property.paymentsbillingservice;

import com.service.property.paymentsbillingservice.ratelimiter.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class PaymentsBillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsBillingServiceApplication.class, args);
    }

}
