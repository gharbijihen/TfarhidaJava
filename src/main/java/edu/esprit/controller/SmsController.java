package edu.esprit.controller;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;
    public class SmsController {
        // Find your Account Sid and Token at twilio.com/console
        public static final String ACCOUNT_SID = "AC1e6df8a1bd40d90a87bc537a2e8e7500";
        public static final String AUTH_TOKEN = "304626d25785b0139ca5168f2ed451b7";

        public static void Sms() {
            System.out.println("Sending SMS now");
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("+21656747798"),
                    new com.twilio.type.PhoneNumber("+13344588731"),


                    "Votre moyen de transport est sous traitement").create();

            System.out.println(message.getSid());
        }
    }

