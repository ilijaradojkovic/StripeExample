package com.example.stripeexample.entity.card;

public record MyCreateCardObject(
        String accountId,
        String externalAccount, //(visa,card)
        String cardNumber,
        String cvc,
        Integer expMonth,
        Integer expYear

) {
}
