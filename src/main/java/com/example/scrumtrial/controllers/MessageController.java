package com.example.scrumtrial.controllers;

import com.example.scrumtrial.Services.MessageService;
import com.example.scrumtrial.Services.UserService;
import com.example.scrumtrial.models.dtos.MessageResponse;
import com.example.scrumtrial.models.dtos.MsgByEmailRequest;
import com.example.scrumtrial.models.dtos.MsgBySmsRequest;
import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("messages/")
public class MessageController {
    private  final Random r = new Random();
    private final MessageService ms;
    private final UserService us;
    private final Faker f;

    public MessageController(UserService us, MessageService ms){
        this.ms = ms;
        this.us = us;
        this.f = new Faker();
    }

    @GetMapping("/sender/email")
    public List<MessageResponse> getMessagesSentByUsr(@RequestBody MsgByEmailRequest req){
        return ms.getAllBySender(req);
    }

    @GetMapping("/sender/sms")
    public List<MessageResponse> getMessagesSentByUsr(@RequestBody MsgBySmsRequest req){
        return ms.getAllBySender(req);
    }

    @GetMapping("/receiver/email")
    public List<MessageResponse> getMessagesReceivedByUsr(@RequestBody MsgByEmailRequest req){
        return ms.getAllByReceiver(req);
    }

    @GetMapping("/receiver/sms")
    public List<MessageResponse> getMessagesReceivedByUsr(@RequestBody MsgBySmsRequest req){
        return ms.getAllByReceiver(req);
    }

    /*
    @GetMapping("/")
    public String user(@CookieValue("loginCred") Integer hash){
        UserEntity tue = new UserEntity(f.name().username(), f.name().nameWithMiddle());
        ur.save(tue);
        return "sucess";
    }

    @GetMapping("/m")
    public String msg(){
        List<UserEntity> usrs = ur.findAll();
        List<UserEntity> toUsers = new LinkedList<>();
        UserEntity fu = usrs.get(r.nextInt(usrs.size()));
        for (int i = r.nextInt(usrs.size()/2); i < r.nextInt(usrs.size()/2, usrs.size()); i++) {
            toUsers.add(usrs.get(i));
        }
        mr.save(new MessageEntity(fu, toUsers, f.lorem().sentence()));
        return "sucess";
    }
    */
}
