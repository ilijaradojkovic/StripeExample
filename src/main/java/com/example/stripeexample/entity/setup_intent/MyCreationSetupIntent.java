package com.example.stripeexample.entity.setup_intent;

public record MyCreationSetupIntent(

        String customerId,
        String paymentMethodId,
        String description,
        Boolean confirm
) {
}
