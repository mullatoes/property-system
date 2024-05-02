package com.service.property.paymentsbillingservice.ratelimiter;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class TenantManagementServiceConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
