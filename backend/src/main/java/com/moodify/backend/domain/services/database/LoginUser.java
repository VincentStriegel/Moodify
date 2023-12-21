package com.moodify.backend.domain.services.database;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser {
    private String credential;
    private String password;

}
