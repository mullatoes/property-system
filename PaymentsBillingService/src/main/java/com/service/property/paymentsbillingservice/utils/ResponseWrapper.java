package com.service.property.paymentsbillingservice.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper<T> {
    private String status;
    private String message;
    private Integer itemCount;
    private T data;

    public ResponseWrapper(String status, String message, Integer itemCount, T data) {
        this.status = status;
        this.message = message;
        this.itemCount = itemCount;
        this.data = data;
    }

    public ResponseWrapper(String status, String message) {
        this.status = status;
        this.message = message;
    }



}
