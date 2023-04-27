package com.example.scrumtrial.services;

import com.example.scrumtrial.models.dtos.*;
import com.example.scrumtrial.models.entities.UserEntity;
import com.example.scrumtrial.models.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserService {
    private final UserRepository uRep;

    public UserService(UserRepository uRep){
        this.uRep = uRep;
    }

    public UserEntity findUserByEmail(String email) throws Exception{
        return uRep.findUserEntityByEmail(email).orElseThrow();
    }

    public UserEntity findUserBySms(String sms) throws Exception{
        return uRep.findUserEntityBySms(sms).orElseThrow();
    }

    public void saveUser(CreateUserWithEmailReq req){
        UserEntity e = new UserEntity();
        e.setName(req.getName());
        e.setEmail(req.getEmail());
        e.setLastLogin(ZonedDateTime.now());
    }

    public void saveUser(CreateUserWithSmsReq req){
        UserEntity e = new UserEntity();
        e.setName(req.getName());
        e.setSms(req.getSms());
        e.setLastLogin(ZonedDateTime.now());
    }

    public void updateUser(UpdateUserEmailReq req){
        UserEntity e = new UserEntity();
        e.setEmail(req.getEmail());
        e.setLastLogin(ZonedDateTime.now());
    }

    public void updateUser(UpdateUserSmsReq req) {
        UserEntity e = new UserEntity();
        e.setSms(req.getSms());
        e.setLastLogin(ZonedDateTime.now());
    }

    public void deleteUser(DeleteUserByEmailReq req){
        UserEntity e = new UserEntity();
        e.setEmail(req.getEmail());
        e.setLastLogin(ZonedDateTime.now());
    }

    public void deleteUser(DeleteUserBySmsReq req){
        UserEntity e = new UserEntity();
        e.setSms(req.getSms());
        e.setLastLogin(ZonedDateTime.now());
    }

    public void deleteUser(DeleteUserById req) {
        UserEntity e = new UserEntity();
        e.setId(req.getId());
        e.setName(req.getName());
        e.setLastLogin(ZonedDateTime.now());
    }


}
