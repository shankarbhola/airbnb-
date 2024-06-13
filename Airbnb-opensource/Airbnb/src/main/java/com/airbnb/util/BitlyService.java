package com.airbnb.util;

import com.opsmatters.bitly.Bitly;
import com.opsmatters.bitly.api.model.v4.CreateBitlinkResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BitlyService {

    @Value("${bitly_token}")
    String BITLY_TOKEN;

    private Bitly client;

    @PostConstruct
    public void setup() {
        client = new Bitly(BITLY_TOKEN);
    }

    public String shortLink(String longURL) {

        try {
            CreateBitlinkResponse response = client.bitlinks().shorten(longURL).get();
            String link = response.getLink();
            return link;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
