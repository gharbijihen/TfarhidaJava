package edu.esprit.controller;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class SmsController {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACe1066284a1c153e92f884d183dec7869";
    public static final String AUTH_TOKEN = "c86cf0b4ecfe7c4685f7dbcbf59d7528";

    public static void Sms() {
        System.out.println("Sending SMS now");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21696638088"),
                new com.twilio.type.PhoneNumber("+15098222653"),


                "Vous avez reçu une réponse à votre réclamation").create();

        System.out.println(message.getSid());
    }
}
