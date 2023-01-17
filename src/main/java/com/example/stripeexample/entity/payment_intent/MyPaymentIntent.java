package com.example.stripeexample.entity.payment_intent;

public record MyPaymentIntent (

        String customerId,
        Long amount,
        String currency,
        String paymentMethodId

){
}
