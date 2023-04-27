package com.example.scrumtrial.models.dtos;

import lombok.Data;

@Data
public class DeleteUserBySmsReq {
    private String sms;
    private Boolean active=false;

}
