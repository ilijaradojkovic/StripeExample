package com.example.stripeexample.entity.transfer_reversal;

public record MyTransferReversalObject(
        String transferId,
        Long amount
) {
}
