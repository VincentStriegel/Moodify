package com.moodify.backend.services.database.postgres;

import com.moodify.backend.services.database.objects.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDO, Long> {

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    UserDO getUserByEmail(String email);

    UserDO getUserByUsername(String username);

    UserDO findById(long id);

    List<UserDO> findAllByUsernameLikeOrEmailLike(String username, String email);

}
