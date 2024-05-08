package edu.esprit.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
public class Payement {
    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51OqD2zFwwP47unkPDwjI0VW2CAMmqra1xmdfGVzzC2SgbMxKc2O36huNoEJiR6qKmlndFVRWRwBqBn03Bsj5PRl500Sy5RjUr8";

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
