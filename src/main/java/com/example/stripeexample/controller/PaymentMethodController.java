package com.example.stripeexample.controller;


import com.example.stripeexample.entity.MyPaymentMethod;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment_method")
public class PaymentMethodController {

    private final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";

    @PostMapping
    public String createPaymentMethod() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> card = new HashMap<>();
        card.put("number", "4242424242424242");
        card.put("exp_month", 8);
        card.put("exp_year", 2023);
        card.put("cvc", "314");
        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);

       return
                PaymentMethod.create(params).toJson();
    }

    @GetMapping
    public String getAllPaymentMethods() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");

       return
                PaymentMethod.list(params).toJson();
    }

    @GetMapping("/{id}")
    public String retrivePaymentMethod(@PathVariable String id) throws StripeException {
        Stripe.apiKey = stripeKey;
        return PaymentMethod.retrieve(id).toJson();

    }

    @PutMapping("/attach/{id}")
    public String attachPaymentMethodToCustomer(@RequestBody MyPaymentMethod myPaymentMethod, @PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentMethod paymentMethod =
                PaymentMethod.retrieve(
                        "pm_1MQbdxFtmfxzG6wMPTaucJ8t"
                );

        Map<String, Object> params = new HashMap<>();
        params.put("customer", "cus_NAsQhTcOVeXaac");

        PaymentMethod updatedPaymentMethod =
                paymentMethod.attach(params);
        return  updatedPaymentMethod.toJson();
    }
}

