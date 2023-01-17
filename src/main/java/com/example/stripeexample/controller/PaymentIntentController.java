package com.example.stripeexample.controller;

import com.example.stripeexample.entity.payment_intent.MyPaymentIntent;
import com.example.stripeexample.entity.payment_intent.MyUpdatePaymentIntent;
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

    @PatchMapping()
    public String updatePaymentInetnt(@RequestBody MyUpdatePaymentIntent myUpdatePaymentIntent) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentIntent paymentIntent = PaymentIntent.retrieve("pi_3MQuhmFtmfxzG6wM0l0QE6Y0");




        //PaymentIntent updatedPaymentIntent = paymentIntent.update(params);
        return "";
    }

   // Confirm that your customer intends to pay with current or provided payment method.
   // Upon confirmation, the PaymentIntent will attempt to initiate a payment.
    @PatchMapping("/{id}")
    public String confirmPaymentIntent(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);



        PaymentIntent updatedPaymentIntent =
                paymentIntent.confirm();
        return paymentIntent.toJson();
    }

    //Capture the funds of an existing uncaptured PaymentIntent when its status is requires_capture.
    //Uncaptured PaymentIntents will be canceled a set number of days after they are created (7 by default).
    //Capture the funds of an existing uncaptured PaymentIntent when its status is requires_capture.
    //Uncaptured PaymentIntents will be canceled a set number of days after they are created (7 by default).
    //Only a PaymentIntent with one of the following statuses may be captured: requires_capture
    @PatchMapping("/capture/{id}")
    public String capturePaymentIntent(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);

        PaymentIntent updatedPaymentIntent = paymentIntent.capture();

        return updatedPaymentIntent.toJson();
    }
}
