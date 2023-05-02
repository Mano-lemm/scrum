package com.example.scrumtrial.models.repositories;

import com.example.scrumtrial.models.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, Long> {
    public Optional<UserEntity> findUserEntityByEmail(String email);
    public Optional<UserEntity> findUserEntityBySms(String sms);


}
