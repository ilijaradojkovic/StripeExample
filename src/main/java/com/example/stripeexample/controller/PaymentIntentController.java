package com.example.stripeexample.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;


//Stripe is heavily investing in PaymentIntents as the integration path going forward

@RestController
@RequestMapping("/payment-intent")
public class PaymentIntentController {


    private  final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping("/{customerId}")
    //obavezna polja: currency,customer,paymentmethod bez ovoga nece da bude u stanju da mi idemo confirm
    public String createPaymentIntent(

            @PathVariable("customerId") String customerId

    ) throws StripeException {

        PaymentIntentCreateParams params=PaymentIntentCreateParams.builder()
                .setCustomer(customerId)
                .setAmount(500L)
                .setCurrency("usd")
                .setPaymentMethod("card_1MQtuTFtmfxzG6wM4KvJFrCp")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        //moramo da confirm kako bi prosla
        paymentIntent.confirm();
        return paymentIntent.toJson();

    }

    @DeleteMapping("/{id}")
    public String cancelPaymentIntent(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentIntent paymentIntent =
                PaymentIntent.retrieve(
                        id
                );

        PaymentIntent updatedPaymentIntent =
                paymentIntent.cancel();
        return updatedPaymentIntent.toJson();
    }
}
