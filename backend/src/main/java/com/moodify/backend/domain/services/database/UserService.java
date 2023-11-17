package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final DatabaseService DATABASE_SERVICE;

    @Autowired
    public UserService(DatabaseService DATABASE_SERVICE) {
        this.DATABASE_SERVICE = DATABASE_SERVICE;
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
