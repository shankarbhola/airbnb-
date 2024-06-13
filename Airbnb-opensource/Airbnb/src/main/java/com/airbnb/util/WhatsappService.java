package com.airbnb.util;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.math.BigDecimal;

@Service
public class WhatsappService {

    @Value("${twilio.whatsapp.ACCOUNT_SID}")
    public String ACCOUNT_SID;

    @Value("${twilio.whatsapp.AUTH_TOKEN}")
    public String AUTH_TOKEN;

    public void sendWhatsappMessage(String phoneNumber,String msg, String link){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:"+phoneNumber),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                msg).setMediaUrl(link).create();

        System.out.println("Whatsapp SID:"+message.getSid());
    }
}
