package com.example.stripeexample.controller;

import com.example.stripeexample.entity.MyCustomer;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class Controller {


    private String stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";

    @PostMapping
    public String createCustomer(@RequestBody MyCustomer myCustomer) throws StripeException {

        Stripe.apiKey= stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("description",myCustomer.description());
        params.put("email", myCustomer.email());
        params.put("name", myCustomer.name());
        params.put("phone", myCustomer.phoneNumber());
        params.put("balance", myCustomer.balance());

        return Customer.create(params).toJson();
    }


}




