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
public class messageController {
    private  final Random r = new Random();
    private final MessageService ms;
    private final UserService us;
    private final Faker f;

    public messageController(UserService us, MessageService ms){
        this.ms = ms;
        this.us = us;
        this.f = new Faker();
    }

    @GetMapping("/usr")
    public List<MessageResponse> getMessagesSentByUsr(@RequestBody MsgByEmailRequest req){
        return ms.getAllSentBy(req);
    }

    @GetMapping("/usr")
    public List<MessageResponse> getMessagesSentByUsr(@RequestBody MsgBySmsRequest req){
        return ms.getAllSentBy(req);
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
