package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.UserTO;
import com.moodify.backend.domain.services.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final DatabaseService DATABASE_SERVICE;
    private final PasswordEncoder ENCODER;

    @Autowired
    public UserService(DatabaseService DATABASE_SERVICE, PasswordEncoder ENCODER) {
        this.DATABASE_SERVICE = DATABASE_SERVICE;
        this.ENCODER = ENCODER;
    }
    public UserTO getUser(long id) {
        User user = this.DATABASE_SERVICE.findById(id);
        UserTO userTO = new UserTO();

        userTO.setEmail(user.getEmail());
        userTO.setUsername(user.getUsername());
        //TODO set PersonalLibrary
        return userTO;
    }
}
