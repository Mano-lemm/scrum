package com.example.scrumtrial.models.dtos;

import lombok.Data;

@Data
public class DeleteUserByEmailReq {
    private String email;
    private Boolean active = false;
}
