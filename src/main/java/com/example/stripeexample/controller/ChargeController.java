package com.example.stripeexample.controller;


import com.example.stripeexample.entity.charge.MyCharge;
import com.example.stripeexample.entity.charge.MyUpdateCharge;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/charge")
public class ChargeController {

    private final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";

    @PostMapping
    public String create(@RequestBody  MyCharge myCharge) throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", myCharge.amount());
        params.put("currency", myCharge.currency());
        params.put("source", myCharge.source());
        params.put("description", myCharge.description());
        params.put("customer",myCharge.customer());
        //po default-u je true
        params.put("capture", false);
        Charge charge = Charge.create(params);
        return charge.toJson();
    }

    @GetMapping("/{id}")
    public String retrve(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        Charge charge = Charge.retrieve(id);

        return charge.toJson();
    }

    @PatchMapping
    public String update(@RequestBody MyUpdateCharge myUpdateCharge) throws StripeException {

        Stripe.apiKey = stripeKey;

        Charge charge = Charge.retrieve("ch_3MQuk4FtmfxzG6wM0HixJFr3");

        Map<String, Object> params = new HashMap<>();
        params.put("customer", myUpdateCharge.customer());
        params.put("description", myUpdateCharge.description());

        Charge updatedCharge = charge.update(params);
        return updatedCharge.toJson();

    }

    @PatchMapping("/{id}")
    public String captureCharge(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        Charge charge = Charge.retrieve(id);

        Charge updatedCharge = charge.capture();
        return updatedCharge.toJson();
    }

    @GetMapping
    public String listCharges() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        ChargeCollection charges = Charge.list(params);
       return charges.toJson();
    }
}
