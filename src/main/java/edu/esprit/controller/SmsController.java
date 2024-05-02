package edu.esprit.controller;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class SmsController {
    // Find your Account Sid and Token at twilio.com/console


    public static void Sms() {
        System.out.println("Sending SMS now");
       
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21696638088"),
                new com.twilio.type.PhoneNumber("+15098222653"),


                "Vous avez reçu une réponse à votre réclamation").create();

        System.out.println(message.getSid());
    }
}
