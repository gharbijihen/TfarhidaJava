package edu.esprit.controller.Logement;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
public class SmsController {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "ACb0d367046da4463fd88b43989996bc8d";
    public static final String AUTH_TOKEN = "6d0a3d7e12b11d0f413fb748361e00b0";

    public static void Sms() {
        System.out.println("Sending SMS now");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21658978570"),
                new com.twilio.type.PhoneNumber("+19725841982"),


                "Votre demande est en cours de traitement ").create();

        System.out.println(message.getSid());
    }
    public static void SmsRéfuse() {
        System.out.println("Sending SMS now");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21658978570"),
                new com.twilio.type.PhoneNumber("+19725841982"),


                "Votre demande est refusée ").create();

        System.out.println(message.getSid());
    }public static void SmsAccepter() {
        System.out.println("Sending SMS now");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21658978570"),
                new com.twilio.type.PhoneNumber("+19725841982"),


                "Votre demande est accepte ").create();

        System.out.println(message.getSid());
    }


}