package com.example.stripeexample.entity.connect;

public record  MyConnectPaymentIntent(
        String accountId,
        Long accountAmount,
        Long toBePayedAmount,
        String customerId
){
}
