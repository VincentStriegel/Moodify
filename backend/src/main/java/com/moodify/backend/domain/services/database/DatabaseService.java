package com.moodify.backend.domain.services.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseService extends JpaRepository<User, Long> {

    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);

    User getUserByEmailAndPassword(String credentials, String password);

    User getUserByUsernameAndPassword(String credentials, String password);
    User findById(long id);

}
