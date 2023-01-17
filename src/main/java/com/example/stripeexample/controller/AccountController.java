package com.example.stripeexample.controller;


import com.example.stripeexample.entity.account.MyAccount;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountCollection;
import com.stripe.param.AccountCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//Account nije isto sto i customer,mi customer-u stavljamo payment intent i time on ima obavezu da plati,
// a account je owner njemu ide isplata preko transfer
//This is an object representing a Stripe account. You can retrieve it to see properties on the account like
// its current e-mail address or if the account is enabled yet to make live charges.

@RestController
@RequestMapping("/account")
public class AccountController {

    private final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping
    public String createAccount(@RequestBody  MyAccount myAccount) throws StripeException {

        Stripe.apiKey = stripeKey;


        AccountCreateParams accountCreateParams=AccountCreateParams.builder()
                        .setEmail(myAccount.email())
                        .setType(myAccount.type())
                        .setCountry(myAccount.country())
                        .setBusinessType(myAccount.business_type())
                        .build();


        Account account = Account.create(accountCreateParams);

        return  account.toJson();
    }

    @GetMapping("/{id}")
    public String retriveAccount(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        Account account = Account.retrieve("acct_1M0N5GFtmfxzG6wM");

        return account.toJson();
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;


        Account account = Account.retrieve(id);

        Account deletedAccount = account.delete();

        return deletedAccount.toJson();

    }
    @PatchMapping("/{id}")
    public String rejectAccount(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        Account account =Account.retrieve(id);

        Map<String, Object> params = new HashMap<>();
        params.put("reason", "fraud");

        Account updatedAccount = account.reject(params);

        return updatedAccount.toJson();
    }

    @GetMapping()
    public  String getAllAccounts() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        AccountCollection accounts = Account.list(params);
        return accounts.toJson();
    }
}
