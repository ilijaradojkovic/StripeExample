package com.example.stripeexample.controller;

import com.example.stripeexample.entity.card.MyCreateCardObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Card;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account/external")
public class AccountExternalController {

    private final String stripeKey = "sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping
    public String createCard(@RequestBody MyCreateCardObject myCreateCardObject) throws StripeException {
        Stripe.apiKey = stripeKey;

        Account account =
                Account.retrieve(myCreateCardObject.accountId());


        Map<String, Object> params = new HashMap<>();
        params.put(
                "external_account",
                "tok_mastercard_debit"
        );

        Card card = (Card) account.getExternalAccounts().create(params);
        return card.toJson();
    }
}
