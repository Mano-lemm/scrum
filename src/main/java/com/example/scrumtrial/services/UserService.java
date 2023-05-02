package com.example.scrumtrial.services;

import com.example.scrumtrial.models.dtos.*;
import com.example.scrumtrial.models.entities.UserEntity;
import com.example.scrumtrial.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository uRep;
    private final PasswordEncoder encoder;

    public UserService(UserRepository uRep, @Qualifier("passwordEncoder") PasswordEncoder encoder){
        this.uRep = uRep;
        this.encoder = encoder;
    }

    @Override
    public UserDetails findUserEntityByEmail(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = uRep.findUserEntityByEmail(email);
        if (user.isPresent()) {
            UserEntity appUser = user.get();
            return User.withUsername(appUser.getEmail()).password(appUser.getPassword()).authorities("USER").build();
        } else {
            throw new UsernameNotFoundException(String.format("Email[%s] not found", s));
        }
    }

    @Override
    public UserDetails findUserEntityBySms(String sms) throws UsernameNotFoundException {
        Optional<UserEntity> user = uRep.findUserEntityBySms(sms);
        if (user.isPresent()) {
            UserEntity appUser = user.get();
            return User.withUsername(appUser.getSms()).password(appUser.getPassword()).authorities("USER").build();
        } else {
            throw new UsernameNotFoundException(String.format("GSM number[%s] not found", s));
        }
    }
    public UserEntity findUserByEmail(String email) throws UsernameNotFoundException{
        return uRep.findUserEntityByEmail(email).orElseThrow();
    }

    public UserEntity findUserBySms(String sms) throws Exception{
        return uRep.findUserEntityBySms(sms).orElseThrow();
    }


    // i updated this one
    // the lastlogin should be set by the controller that gets the request for either
    // a login or a registration
    public void saveUser(CreateUserWithEmailReq req){
        UserEntity e = new UserEntity();
        e.setName(req.getName());
        e.setEmail(req.getEmail());
        uRep.save(e);
    }

    public void saveUser(CreateUserWithSmsReq req){
        UserEntity e = new UserEntity();
        e.setName(req.getName());
        e.setSms(req.getSms());
        uRep.save(e);
    }

    public void updateUser(UpdateUserEmailReq req){
        UserEntity e = new UserEntity();
        e.setEmail(req.getEmail());
        uRep.save(e);
    }

    public void updateUser(UpdateUserSmsReq req) {
        UserEntity e = new UserEntity();
        e.setSms(req.getSms());
    }

    public void deleteUser(DeleteUserByEmailReq req){
        UserEntity e = new UserEntity();
        e.setEmail(req.getEmail());
    }

    public void deleteUser(DeleteUserBySmsReq req){
        UserEntity e = new UserEntity();
        e.setSms(req.getSms());

    }

    public void deleteUser(DeleteUserById req) {
        UserEntity e = new UserEntity();
        e.setId(req.getId());
        e.setName(req.getName());

    }

        GrantedAuthority admin = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        };
        List<GrantedAuthority> roles = List.of(
                admin
        );

        Set<GrantedAuthority> roles = foundUser.get().getRoles()
                .stream()
                .map(r -> (GrantedAuthority) () -> r)
                .collect(Collectors.toSet());

        return new User (foundUser.get().getEmail(), encoder.encode(foundUser.get().getPassword()), roles);
    }
}
