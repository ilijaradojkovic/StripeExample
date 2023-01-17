package com.example.stripeexample.entity;

public record MyPaymentMethod(
        String number,
        String exp_month,
        String exp_year,
        String cvc

) {
}
