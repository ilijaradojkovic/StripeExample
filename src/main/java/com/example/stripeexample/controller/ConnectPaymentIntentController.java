package com.example.stripeexample.controller;


import com.example.stripeexample.entity.connect.MyConnectPaymentIntent;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-intent/connect")
public class ConnectPaymentIntentController {
    private  final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping
    public String createConnectPayment(@RequestBody  MyConnectPaymentIntent myConnectPaymentIntent) throws StripeException {

        Stripe.apiKey=stripeKey;

        //ne moze fee da stoji  i set amount u transferData

        PaymentIntentCreateParams paymentIntentParams =
                PaymentIntentCreateParams.builder()
                        .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
                        .setAmount(myConnectPaymentIntent.toBePayedAmount())
                        .setCurrency("eur")
                        .setCustomer(myConnectPaymentIntent.customerId())
                        .setPaymentMethod(myConnectPaymentIntent.paymentMethodId())
                        .setApplicationFeeAmount(myConnectPaymentIntent.fee())
                        .setConfirm(true)
                        .setTransferData(
                                PaymentIntentCreateParams.TransferData.builder()
                                        .setDestination(myConnectPaymentIntent.accountId())
                                        .build())
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);
        return  paymentIntent.toJson();
    }
}
