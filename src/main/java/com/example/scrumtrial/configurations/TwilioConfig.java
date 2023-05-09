package com.example.scrumtrial.configurations;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    @Bean
    public Service getSid(@Value("${TWILIO_ACCOUNT_SID}") String sid, @Value("${TWILIO_AUTH_TOKEN}") String token, @Value("${TWILIO_VERIFICATION_SSID}") String ssid){
        Twilio.init(sid, token);
        return Service.fetcher(ssid).fetch();
    }
}
