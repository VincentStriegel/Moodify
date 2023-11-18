package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.UserTO;
import com.moodify.backend.domain.services.database.databaseobjects.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface DatabaseService {
    void saveUser(User user) throws Exception;

    long loginUser(@RequestBody LoginUser loginUser) throws Exception;

    UserTO getUser(long id);
}
