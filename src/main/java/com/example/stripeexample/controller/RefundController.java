package com.example.stripeexample.controller;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.model.RefundCollection;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/refund")
public class RefundController {
    private final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping("/{id}")
    public String createRefund(@PathVariable("id") String chargeId) throws StripeException {


        Stripe.apiKey =stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put(
                "charge",
                chargeId
        );

        Refund refund = Refund.create(params);
        return refund.toJson();
    }

    @GetMapping("/{id}")
    public String retriveRefund(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey =stripeKey;
        Refund refund =
                Refund.retrieve(id);
        return refund.toJson();
    }

    @PatchMapping("/{id}")
    public String updateRefund(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey =stripeKey;

        Refund refund = Refund.retrieve(id);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("reason","Some reason");

        Refund updatedRefund = refund.update(metadata);
        return  updatedRefund.toJson();
    }

    @DeleteMapping("/{id}")
    public String cancelRefund(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey =stripeKey;

        Refund refund =
                Refund.retrieve(id);

        Refund updatedRefund = refund.cancel();
        return updatedRefund.toJson();
    }

    @GetMapping()
    public String listRefunds() throws StripeException {
        Stripe.apiKey =stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        RefundCollection refunds = Refund.list(params);
        return refunds.toJson();
    }

}
