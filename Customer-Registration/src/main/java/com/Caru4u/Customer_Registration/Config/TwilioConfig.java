package com.Caru4u.Customer_Registration.Config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @PostConstruct
    public void init() {
        if (accountSid == null || authToken == null) {
            throw new IllegalStateException("Twilio credentials are not set");
        }
        Twilio.init(accountSid, authToken);
    }


}
