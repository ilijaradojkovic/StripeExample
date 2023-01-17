package com.example.stripeexample.entity.charge;

public record MyCharge(
        String amount,
        String currency,
        String customer,
        String description,
//        String invoice,
//        Map<String,Object> metadata,
//        String payment_intent,
        String source

) {
}
