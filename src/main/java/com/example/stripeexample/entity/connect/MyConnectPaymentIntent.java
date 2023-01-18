package com.example.stripeexample.entity.connect;

public record  MyConnectPaymentIntent(
        String accountId,
        Long fee,
        Long toBePayedAmount,
        String customerId,
        String paymentMethodId
){
}
