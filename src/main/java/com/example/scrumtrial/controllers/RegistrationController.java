package com.example.scrumtrial.controllers;

import com.example.scrumtrial.models.dtos.*;
import com.example.scrumtrial.services.UserService;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.Service;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private Service ssid;
    private final UserService uService;
    private Verification verification;
    private VerificationCheck vc;

    public RegistrationController(@Value("${TWILIO_ACCOUNT_SID}") String sid, @Value("${TWILIO_AUTH_TOKEN}") String token, UserService uService, InMemoryUserDetailsManager iudm){
        this.uService = uService;
        this.ssid = Service.creator("verificationService").create();
        Twilio.init(sid, token);
    }

    @PostMapping("/usr/getCode/email")
    public ResponseEntity<?> createUserWithEmail(@RequestBody CreateUserWithEmailReq req){
        // TODO: FIX
        try {
            verification = Verification.creator(
                    ssid.getSid(),
                    req.getEmail(),
                    "email"
            ).create();
        } catch (Exception e){
            // TODO: FULL ERROR LOGGING
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().body(new LoginReply().error(String.valueOf(Optional.of("Failed to send authentication"))));
    }

    @GetMapping("/usr/checkCode")
    public ResponseEntity<?> checkUserCode(@RequestBody CreateUserWithEmailReq req){
        try {
            vc = VerificationCheck.creator(
                    ssid.getSid())
                    .setTo(req.getEmail())
                    .setCode(req.getCode())
                    .create();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        String sT = String.valueOf(Objects.hash(req.getEmail(), ZonedDateTime.now()));
        uService.saveUser(req);
        return ResponseEntity.ok(new LoginReply().success(true).sessionToken(String.valueOf(Optional.of(sT))));
    }

    @PostMapping("/usr/getCode/sms")
    public ResponseEntity<?> createUserWithSms(@RequestBody CreateUserWithSmsReq req){
        try {
            verification = Verification.creator(
                    ssid.getSid(),
                    req.getSms(),
                    "sms"
            ).createAsync().get();
        } catch (Exception e){
            // TODO: FULL ERROR LOGGING
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        if(verification.getValid()){
            String sT = String.valueOf(Objects.hash(req.getSms(), ZonedDateTime.now()));
            uService.saveUser(req);
            return ResponseEntity.ok(new LoginReply().success(true).sessionToken(String.valueOf(Optional.of(sT))));
        }
        return ResponseEntity.badRequest().body(new LoginReply().error(String.valueOf(Optional.of("Failed to authenticate"))));
    }

    @PostMapping("/usr/update/email")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserEmailReq req){
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
            uService.updateUser(req);
            return ResponseEntity.ok(new LoginReply().success(true).sessionToken(String.valueOf(Optional.of(sT))));
        }
        return ResponseEntity.badRequest().body(new LoginReply().error(String.valueOf(Optional.of("Failed to authenticate"))));
    }

    @PostMapping("/usr/update/sms")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserSmsReq req){
        try {
            verification = Verification.creator(
                    ssid.getSid(),
                    req.getSms(),
                    "sms"
            ).createAsync().get();
        } catch (Exception e){
            // TODO: FULL ERROR LOGGING
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        if(verification.getValid()){
            String sT = String.valueOf(Objects.hash(req.getSms(), ZonedDateTime.now()));
            uService.updateUser(req);
            return ResponseEntity.ok(new LoginReply().success(true).sessionToken(String.valueOf(Optional.of(sT))));
        }
        return ResponseEntity.badRequest().body(new LoginReply().error(String.valueOf(Optional.of("Failed to authenticate"))));
    }

    @PostMapping("/usr/delete/sms")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserBySmsReq req){
        try {
            verification = Verification.creator(
                    ssid.getSid(),
                    req.getSms(),
                    "sms"
            ).createAsync().get();
        } catch (Exception e){
            // TODO: FULL ERROR LOGGING
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        if(verification.getValid()){
            String sT = String.valueOf(Objects.hash(req.getSms(), req.getSms(), ZonedDateTime.now()));
            uService.deleteUser(req);
            return ResponseEntity.ok(new LoginReply().success(true).sessionToken(String.valueOf(Optional.of(sT))));
        }
        return ResponseEntity.badRequest().body(new LoginReply().error(String.valueOf(Optional.of("Failed to authenticate"))));
    }

    @PostMapping("/usr/delete/email")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserByEmailReq req){
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
            String sT = String.valueOf(Objects.hash(req.getEmail(), req.getActive(), ZonedDateTime.now()));
            uService.deleteUser(req);
            return ResponseEntity.ok(new LoginReply().success(true).sessionToken(String.valueOf(Optional.of(sT))));
        }
        return ResponseEntity.badRequest().body(new LoginReply().error(String.valueOf(Optional.of("Failed to authenticate"))));
    }

//    @PostMapping("/usr/delete/account")
//    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserById req){
//        try {
//            verification = Verification.creator(
//                    ssid.getSid(),
//                    req.getName(),
//                    "email"
//            ).createAsync().get();
//        } catch (Exception e){
//            // TODO: FULL ERROR LOGGING
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            return ResponseEntity.badRequest().build();
//        }
//        if(verification.getValid()){
//            Long sT = Long.valueOf(Objects.hash(req.getId(), req.getName()));
//            uService.deleteUser(req);
//            return ResponseEntity.ok(new LoginReply().success(true).sessionToken(sT));
//        }
//        return ResponseEntity.badRequest().body(new LoginReply().error(String.valueOf(Optional.of("Failed to authenticate"))));
//    }
}
