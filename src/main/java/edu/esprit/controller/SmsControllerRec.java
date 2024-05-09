package edu.esprit.controller;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class SmsControllerRec {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC2f8b7694f7aeb517fe76fbd35e982afd";
    public static final String AUTH_TOKEN = "3e76c311f1a7f085f6b7ec0b6ce4e3eb";

    public static void Sms() {
        System.out.println("Sending SMS now");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21696638088"),
                new com.twilio.type.PhoneNumber("+12176276732"),


                "Vous avez reçu une réponse à votre réclamation").create();

        System.out.println(message.getSid());
    }
}
