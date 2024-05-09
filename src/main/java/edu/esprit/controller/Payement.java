package edu.esprit.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
public class Payement {
    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51MxqJZBXISmXcpqPHd8tHgrQg1w1nk6MJ2PpJwvXd1k6cyRfShvhwKiRwx1uIat0vvFTfqCTWu7usXQQeMrneqpk00URiJYKli";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
