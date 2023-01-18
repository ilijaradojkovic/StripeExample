package com.example.stripeexample.controller;

import com.example.stripeexample.entity.transfer.MyTransferObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import com.stripe.model.TransferCollection;
import com.stripe.param.TransferCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//A Transfer object is created when you move funds between Stripe accounts as part of Connect.
//Kada hocu ja sa mog akaunta da prebacim nesto na drugi connect akaunt
@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping()
    public String createTransferObject(@RequestBody MyTransferObject transferObject) throws StripeException {
        Stripe.apiKey = stripeKey;

        TransferCreateParams params=TransferCreateParams.builder()
                .setAmount(transferObject.price())
                .setCurrency("usd")
                .setDestination(transferObject.accountId())
                .build();


        Transfer transfer = Transfer.create(params);
        return transfer.toJson();
    }
    @GetMapping("/{id}")
    public String retriveTransferObject(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        Transfer transfer = Transfer.retrieve(id);

        return  transfer.toJson();
    }

    @GetMapping
    public String listTransfers() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        TransferCollection transfers = Transfer.list(params);
        return  transfers.toJson();
    }
}
