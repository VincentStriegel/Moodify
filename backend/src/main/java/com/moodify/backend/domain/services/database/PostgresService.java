package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.UserTO;
import com.moodify.backend.domain.services.database.databaseobjects.User;
import com.moodify.backend.domain.services.exceptions.RegisteredEmailException;
import com.moodify.backend.domain.services.exceptions.RegisteredUsernameException;
import com.moodify.backend.domain.services.exceptions.UserCredentialsException;
import com.moodify.backend.domain.services.exceptions.WrongPasswordException;
import com.moodify.backend.domain.services.security.EmailValidator;
import com.moodify.backend.domain.services.security.PasswordEncoder;
import com.moodify.backend.domain.services.security.PasswordValidator;
import com.moodify.backend.domain.services.security.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class PostgresService implements DatabaseService {
    private final DatabaseRepository DATABASE_REPOSITORY;
    private final PasswordEncoder ENCODER;


    @Autowired
    public PostgresService(DatabaseRepository DATABASE_REPOSITORY, PasswordEncoder ENCODER) {
        this.DATABASE_REPOSITORY = DATABASE_REPOSITORY;
        this.ENCODER = ENCODER;
    }

    @Override
    public void saveUser(User user) throws Exception {


        EmailValidator.validateEmail(user.getEmail());
        UsernameValidator.validateUsername(user.getUsername());
        PasswordValidator.validatePassword(user.getPassword());


        if (this.DATABASE_REPOSITORY.existsUserByEmail(user.getEmail())) {
            throw new RegisteredEmailException();
        }

        if (this.DATABASE_REPOSITORY.existsUserByUsername(user.getUsername())) {
            throw new RegisteredUsernameException();
        }


        String hashed = this.ENCODER.encode(user.getPassword());
        user.setPassword(hashed);

        this.DATABASE_REPOSITORY.save(user);
    }

    @Override
    public long loginUser(@RequestBody LoginUser loginUser) throws Exception {

        String loginEmail = loginUser.getCredential();
        String loginUsername = loginUser.getCredential();
        String loginPassword = loginUser.getPassword();

        User userByEmail = this.DATABASE_REPOSITORY.getUserByEmail(loginEmail);
        if (exists(userByEmail)) {

            String hashedPassword = userByEmail.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }

            return userByEmail.getId();
        }

        User userByUsername = this.DATABASE_REPOSITORY.getUserByUsername(loginUsername);
        if (exists(userByUsername)) {

            String hashedPassword = userByUsername.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }

            return userByUsername.getId();
        }

        throw new UserCredentialsException();

    }

    @Override
    public UserTO getUser(long id) {
        User user = this.DATABASE_REPOSITORY.findById(id);
        UserTO userTO = new UserTO();

        userTO.setEmail(user.getEmail());
        userTO.setUsername(user.getUsername());
        //TODO set PersonalLibrary
        return userTO;
    }

    private boolean exists(User user) {
        return user != null;
    }
}
