package com.example.stripeexample.entity.account;


import com.stripe.param.AccountCreateParams;

public record MyAccount(

        AccountCreateParams.Type type,
        String country, //2 digit code,
        String email,
        AccountCreateParams.BusinessType business_type

) {
}
