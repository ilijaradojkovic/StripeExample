package com.example.stripeexample.controller;


import com.example.stripeexample.entity.transfer_reversal.MyTransferReversalObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import com.stripe.model.TransferReversal;
import com.stripe.model.TransferReversalCollection;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/transfer/reversal")
public class TransferReversalController {
    //Stripe Connect platforms can reverse transfers made to a connected account, either entirely or partially,
    // and can also specify whether to refund any related application fees.
    // Transfer reversals add to the platform's balance and subtract from the destination account's balance.
    //
    //Reversing a transfer that was made for a destination charge is allowed only up to the amount of the charge.
    // It is possible to reverse a transfer_group transfer only if the destination account has enough balance to cover the reversal.

    private final String stripeKey = "sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping
    public String createTransferReversal(@RequestBody MyTransferReversalObject myTransferReversalObject) throws StripeException {

        Stripe.apiKey = stripeKey;

        Transfer transfer =
                Transfer.retrieve(
                        myTransferReversalObject.transferId()
                );

        Map<String, Object> params = new HashMap<>();
        params.put("amount", myTransferReversalObject.amount());

        TransferReversal transferReversal =
                transfer.getReversals().create(params);

        return transferReversal.toJson();
    }

    @GetMapping("/{transferId}/{reversalId}")
    public String retriveTransferReversal(@PathVariable("transferId") String transferId, @PathVariable("reversalId") String reversalId) throws StripeException {
        Stripe.apiKey = stripeKey;

        Transfer transfer = Transfer.retrieve(transferId);

        TransferReversal transferReversal = transfer.getReversals().retrieve(reversalId);

        return transferReversal.toJson();
    }

    @GetMapping("/{transferId}")
    public String listAll(@PathVariable("transferId") String transferId) throws StripeException {
        Stripe.apiKey = stripeKey;

        Transfer transfer =
                Transfer.retrieve(transferId);

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        TransferReversalCollection transferReversals =
                transfer.getReversals().list(params);

        return transferReversals.toJson();
    }


}
