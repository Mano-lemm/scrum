package com.example.scrumtrial.models.dtos;

import lombok.Data;

@Data
public class CheckNewUserEmail {
    private String email;
    private String name;
    private String code;
}