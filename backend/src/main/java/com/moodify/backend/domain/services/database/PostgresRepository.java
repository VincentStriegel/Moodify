package com.moodify.backend.domain.services.database;

import com.moodify.backend.domain.services.database.databaseobjects.LikedAlbumDO;
import com.moodify.backend.domain.services.database.databaseobjects.LikedTrackDO;
import com.moodify.backend.domain.services.database.databaseobjects.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostgresRepository extends JpaRepository<UserDO, Long>{

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    UserDO getUserByEmail(String email);

    UserDO getUserByUsername(String username);



    UserDO findById(long id);

}
