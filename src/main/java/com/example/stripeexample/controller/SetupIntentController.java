package com.example.stripeexample.controller;


import com.example.stripeexample.entity.setup_intent.MyCreationSetupIntent;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.SetupIntent;
import com.stripe.model.SetupIntentCollection;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/setup")
public class SetupIntentController {

    //A SetupIntent guides you through the process of setting up and saving a customer's payment credentials for future payments.
    // For example, you could use a SetupIntent to set up and save your customer's card without immediately collecting a payment.
    // Later, you can use PaymentIntents to drive the payment flow.
    //
    //Create a SetupIntent as soon as you're ready to collect your customer's payment credentials.
    // Do not maintain long-lived, unconfirmed SetupIntents as they may no longer be valid.
    // The SetupIntent then transitions through multiple statuses as it guides you through the setup process.
    //
    //Successful SetupIntents result in payment credentials that are optimized for future payments.
    // For example, cardholders in certain regions may need to be run through Strong Customer Authentication
    // at the time of payment method collection in order to streamline later off-session payments.
    // If the SetupIntent is used with a Customer, upon success, it will automatically attach the resulting payment method to that Customer.
    // We recommend using SetupIntents or setup_future_usage on PaymentIntents to save payment methods in
    // order to prevent saving invalid or unoptimized payment methods.
    //
    //By using SetupIntents, you ensure that your customers experience the minimum set of required friction,
    // even as regulations change over time.

    private final String  stripeKey ="sk_test_51M0N5GFtmfxzG6wMv5rF80PkxBXT7WTOC9fudwDsAdWwws4buKuvv7XlyNTrtIFlV7RkVOesXFLQ2hUUnmticZCd0016lhtl2c";


    @PostMapping
    public String createSetupIntent(@RequestBody MyCreationSetupIntent setupIntent) throws StripeException {

        Stripe.apiKey = stripeKey;


        Map<String, Object> params = new HashMap<>();
        params.put("customer",setupIntent.customerId());
        params.put("description",setupIntent.description());
        params.put("payment_method",setupIntent.paymentMethodId());
        //params.put("confirm",setupIntent.customerId());


        SetupIntent setup = SetupIntent.create(params);

        return setup.toJson();
    }

    @GetMapping("/{id}")
    public String retriveSetupIntent(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        SetupIntent setupIntent = SetupIntent.retrieve(id);
        return setupIntent.toJson();
    }

    //Confirm that your customer intends to set up the current or provided payment method. For example, you would confirm a SetupIntent when a customer hits the “Save” button on a payment method management page on your website.
    //If the selected payment method does not require any additional steps from the customer, the SetupIntent will transition to the succeeded status.
    @PatchMapping("/{id}")
    public String confirmSetupIntent(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        SetupIntent setupIntent = SetupIntent.retrieve(id);

        //setup payment methods if missing

        SetupIntent updatedSetupIntent = setupIntent.confirm();
        return updatedSetupIntent.toJson();
    }

    //A SetupIntent object can be canceled when it is in one of these statuses: requires_payment_method, requires_confirmation, or requires_action.
    //Once canceled, setup is abandoned and any operations on the SetupIntent will fail with an error.
    @DeleteMapping("/{id}")
    public String deleteSetupIntent(@PathVariable("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        SetupIntent setupIntent = SetupIntent.retrieve(id);

        SetupIntent updatedSetupIntent = setupIntent.cancel();

        return updatedSetupIntent.toJson();
    }


    @GetMapping()
    public String listSetupIntents() throws StripeException {
        Stripe.apiKey = stripeKey;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", 3);

        SetupIntentCollection setupIntents =
                SetupIntent.list(params);

        return setupIntents.toJson();
    }

}
