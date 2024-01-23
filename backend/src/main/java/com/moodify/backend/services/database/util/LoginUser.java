package com.moodify.backend.services.database.util;


import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a user attempting to log in.
 * It contains two private fields: credential and password.
 * The credential field represents the username or email of the user.
 * The password field represents the password of the user.
 * Both fields have getter and setter methods.
 */
@Getter
@Setter
public class LoginUser {
    private String credential;
    private String password;

}
