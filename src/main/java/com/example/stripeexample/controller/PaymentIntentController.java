package com.example.stripeexample.controller;

import com.example.stripeexample.entity.payment_intent.MyPaymentIntent;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;


//Stripe is heavily investing in PaymentIntents as the integration path going forward

//A PaymentIntent guides you through the process of collecting a payment from your customer.
//We recommend that you create exactly one PaymentIntent for each order or customer session in your system

@RestController
@RequestMapping("/payment-intent")
public class PaymentIntentController {


    private  final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping()
    //obavezna polja: currency,customer,paymentmethod bez ovoga nece da bude u stanju da mi idemo confirm
    //When confirm=true is used during creation, it is equivalent to creating and confirming the PaymentIntent in the same call.
    public String createPaymentIntent(@RequestBody MyPaymentIntent myPaymentIntent) throws StripeException {
        Stripe.apiKey=stripeKey;
//PaymentIntentCreateParams or Map<String,Object>
        PaymentIntentCreateParams params=PaymentIntentCreateParams.builder()
                .setCustomer(myPaymentIntent.customerId())
                .setAmount(myPaymentIntent.amount())
                .setCurrency(myPaymentIntent.currency())
                .setPaymentMethod(myPaymentIntent.paymentMethodId())
                .setConfirm(false)
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return paymentIntent.toJson();

    }

    @DeleteMapping("/{id}")
    public String cancelPaymentIntent(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentIntent paymentIntent =
                PaymentIntent.retrieve(
                        id
                );

        PaymentIntent updatedPaymentIntent = paymentIntent.cancel();

        return updatedPaymentIntent.toJson();
    }

    @GetMapping("/{id}")
    public String retrivePaymentIntent(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);

        return paymentIntent.toJson();
    }
}
