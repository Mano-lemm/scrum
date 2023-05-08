package com.example.scrumtrial.controllers;

import com.example.scrumtrial.Flow.Services.UserService;
import com.example.scrumtrial.models.dtos.EmailLoginRequest;
import com.example.scrumtrial.models.dtos.LoginReply;
import com.example.scrumtrial.models.dtos.PhoneLoginRequest;
import com.example.scrumtrial.models.entities.UserEntity;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@RestController
public class LoginController {
    private final Service ssid;
    private final UserService uService;

    private LoginController(UserService uService, Service ssid){
        this.uService = uService;
        this.ssid = ssid;
    }

    public Optional<ResponseEntity<LoginReply>> checkUserLoginTime(UserEntity ue){
        if(ChronoUnit.MINUTES.between(ue.getLastLogin(), ZonedDateTime.now()) < 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User tried to log in too recently");
        }
        ue.setLastLogin(ZonedDateTime.now());
        return Optional.empty();
    }

    public void updateLastLogin(UserEntity ue){
        ue.setLastLogin(ZonedDateTime.now());
    }

    @PostMapping("login/usr/email")
    public ResponseEntity<LoginReply> loginAttempt(@RequestBody EmailLoginRequest req){
        try {
            UserEntity ue = uService.findUserByEmail(req.getEmail());
            Optional<ResponseEntity<LoginReply>> or = checkUserLoginTime(ue);
            if(or.isPresent()){
                return or.get();
            }
            updateLastLogin(ue);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email:" +  req.getEmail() + " does not exist");
        }
        Verification verification;
        try {
            verification = Verification.creator(
                    ssid.getSid(),
                    req.getEmail(),
                    "email"
            ).createAsync().get();
        } catch (Exception e){
            // TODO: FULL ERROR LOGGING
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        if(verification.getValid()){
            String sT = String.valueOf(Objects.hash(req.getEmail(), ZonedDateTime.now()));
//            udm.createUser(User
//                    .withUsername(req.getEmail())
//                    .password(sT).build());
            LoginReply l = new LoginReply(true);
            l.setSessionToken(sT);
            return ResponseEntity.ok(l);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to authenticate");
    }

    @PostMapping("login/usr/phone")
    public ResponseEntity<LoginReply> loginAttempt(@RequestBody PhoneLoginRequest req){
        try {
            UserEntity ue = uService.findUserBySms(req.getNumber());
            Optional<ResponseEntity<LoginReply>> or = checkUserLoginTime(ue);
            if(or.isPresent()){
                return or.get();
            }
            updateLastLogin(ue);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with number:" +  req.getNumber() + " does not exist");
        }
        Verification verification;
        try {
            verification = Verification.creator(
                    ssid.getSid(),
                    req.getNumber(),
                    "sms"
            ).createAsync().get();
        } catch (Exception e){
            // TODO: FULL ERROR LOGGING
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        if(verification.getValid()){
            String sT = String.valueOf(Objects.hash(req.getNumber(), ZonedDateTime.now()));
//            udm.createUser(User
//                    .withUsername(req.getNumber())
//                    .password(sT).build());
            LoginReply l = new LoginReply(true);
            l.setSessionToken(sT);
            return ResponseEntity.ok(l);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to authenticate");
    }
}
