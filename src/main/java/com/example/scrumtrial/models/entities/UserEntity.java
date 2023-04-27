package com.example.scrumtrial.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document("users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    Long id;

    @Setter
    String name;
    @Email
    @Indexed(unique = true)
    String email;
    @Indexed(unique = true)
    String sms;
    String password;
    ZonedDateTime lastLogin;
    Boolean active;

    Set<String>roles;

    public UserEntity(String name, String email, String password){
        this.id = (long) Objects.hash(name, email);
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = true;
        this.lastLogin = ZonedDateTime.now();
        this.roles = new HashSet<String>();
        this.roles.add("USER");
    }


}
