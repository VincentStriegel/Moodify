package com.moodify.backend.domain.services.database;

import com.moodify.backend.api.transferobjects.UserTO;
import com.moodify.backend.domain.services.database.databaseobjects.CustomPlaylistDO;
import com.moodify.backend.domain.services.database.databaseobjects.UserDO;
import com.moodify.backend.domain.services.exceptions.registration.RegisteredEmailException;
import com.moodify.backend.domain.services.exceptions.registration.RegisteredUsernameException;
import com.moodify.backend.domain.services.exceptions.login.UserCredentialsException;
import com.moodify.backend.domain.services.exceptions.login.WrongPasswordException;
import com.moodify.backend.domain.services.security.EmailValidator;
import com.moodify.backend.domain.services.security.PasswordEncoder;
import com.moodify.backend.domain.services.security.PasswordValidator;
import com.moodify.backend.domain.services.security.UsernameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class PostgresService implements DatabaseService {
    private final PostgresRepository DATABASE_REPOSITORY;
    private final PasswordEncoder ENCODER;


    @Autowired
    public PostgresService(PostgresRepository DATABASE_REPOSITORY, PasswordEncoder ENCODER) {
        this.DATABASE_REPOSITORY = DATABASE_REPOSITORY;
        this.ENCODER = ENCODER;
    }

    @Override
    public UserDO createUser(UserDO userDO) throws Exception {
        EmailValidator.validateEmail(userDO.getEmail());
        UsernameValidator.validateUsername(userDO.getUsername());
        PasswordValidator.validatePassword(userDO.getPassword());


        if (this.DATABASE_REPOSITORY.existsUserByEmail(userDO.getEmail())) {
            throw new RegisteredEmailException();
        }

        if (this.DATABASE_REPOSITORY.existsUserByUsername(userDO.getUsername())) {
            throw new RegisteredUsernameException();
        }


        String hashed = this.ENCODER.encode(userDO.getPassword());
        userDO.setPassword(hashed);

        return userDO;
    }

    @Override
    public void saveUser(UserDO userDO)  {


        this.DATABASE_REPOSITORY.save(userDO);
    }

    @Override
    public long loginUser(@RequestBody LoginUser loginUser) throws Exception {

        String loginEmail = loginUser.getCredential();
        String loginUsername = loginUser.getCredential();
        String loginPassword = loginUser.getPassword();

        UserDO userDOByEmail = this.DATABASE_REPOSITORY.getUserByEmail(loginEmail);
        if (exists(userDOByEmail)) {

            String hashedPassword = userDOByEmail.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }

            return userDOByEmail.getId();
        }

        UserDO userDOByUsername = this.DATABASE_REPOSITORY.getUserByUsername(loginUsername);
        if (exists(userDOByUsername)) {

            String hashedPassword = userDOByUsername.getPassword();

            boolean passNotEqual = !this.ENCODER.compare(loginPassword, hashedPassword);
            if (passNotEqual) {
                throw new WrongPasswordException();
            }

            return userDOByUsername.getId();
        }

        throw new UserCredentialsException();

    }

    @Override
    public UserTO getUser(long id) {
        UserDO userDO = this.DATABASE_REPOSITORY.findById(id);
        UserTO userTO = new UserTO();

        userTO.setEmail(userDO.getEmail());
        userTO.setUsername(userDO.getUsername());
        //TODO set PersonalLibrary
        return userTO;
    }

    @Override
    public long addCustomPlaylist(long userId, String playlistTitle) {
        UserDO user = this.DATABASE_REPOSITORY.findById(userId);
        user.getCustomPlaylists().add(new CustomPlaylistDO(playlistTitle));

    }


    private boolean exists(UserDO userDO) {
        return userDO != null;
    }
}
