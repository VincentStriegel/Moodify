package com.moodify.backend.domain.services.database;

import com.moodify.backend.domain.services.database.databaseobjects.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseRepository extends JpaRepository<User, Long> {

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    User findById(long id);

}
