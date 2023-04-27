package com.example.scrumtrial.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.time.ZonedDateTime;
import java.util.Objects;

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
    ZonedDateTime lastLogin;
    Boolean active;

    public UserEntity(String name, String email){
        this.id = (long) Objects.hash(name, email);
        this.name = name;
        this.email = email;
        this.active = true;
        this.lastLogin = ZonedDateTime.now();
    }
}
