package edu.esprit.controller;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
    public class SmsController {
        // Find your Account Sid and Token at twilio.com/console
        public static final String ACCOUNT_SID = "AC9ebe82e65c458cdd9134237ce1ea69d8";
        public static final String AUTH_TOKEN = "5baa5838b302ea6970fcb6fe84f81613";

        public static void Sms() {
            System.out.println("Sending SMS now");
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("+21656747798"),
                    new com.twilio.type.PhoneNumber("+16822378789"),


                    "Votre moyen de transport est sous traitement").create();

            System.out.println(message.getSid());
        }
    }

