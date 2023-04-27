package com.example.scrumtrial.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Optional;

@Accessors(fluent = true)
@Data
public class LoginReply {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String sessionToken;
    private Boolean success = false;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String error;
}
