package edu.esprit.controller.controllerMo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsControllerTrans {
        // Find your Account Sid and Token at twilio.com/console
        public static final String ACCOUNT_SID = "ACcb7235d68bbc115bb11fbe7bc8b188c1";
        public static final String AUTH_TOKEN = "5809095800d208eda5cecef6ac8040c0";

        public static void Sms() {
            System.out.println("Sending SMS now");
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("+21656747798"),
                    new com.twilio.type.PhoneNumber("+13343669054"),


                    "Votre moyen de transport est sous traitement").create();

            System.out.println(message.getSid());
        }
    }

