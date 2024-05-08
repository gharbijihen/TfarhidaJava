package Controllers;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsController {
        // Find your Account Sid and Token at twilio.com/console
        public static final String ACCOUNT_SID = "AC0750ed6ed00ede1ef43f4fabe0d6de62";
        public static final String AUTH_TOKEN = "80bed855f92f806b0a761dc635ade3cb";

        public static void Sms() {
            System.out.println("Sending SMS now");
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber("+21695450327"),
                    new com.twilio.type.PhoneNumber("+12673961958"),


                    "Bienvenu chez TFARHIDA ! Nous sommes ravis de vous accueillir dans notre application. TFARHIDA est votre compagnon idéal pour explorer et profiter d'une expérience exceptionnelle").create();

            System.out.println(message.getSid());
        }

    }
