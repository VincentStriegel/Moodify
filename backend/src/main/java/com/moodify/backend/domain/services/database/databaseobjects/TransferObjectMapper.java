package com.moodify.backend.domain.services.database.databaseobjects;

import com.moodify.backend.api.transferobjects.UserTO;

public class TransferObjectMapper {

    public static UserTO toUserTo(UserDO user){
        UserTO userTO = new UserTO();
        userTO.setEmail(user.getEmail());
        userTO.setUsername(user.getUsername());

    }
}
