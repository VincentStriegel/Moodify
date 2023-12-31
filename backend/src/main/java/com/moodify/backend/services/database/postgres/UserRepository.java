package com.moodify.backend.services.database.postgres;

import com.moodify.backend.services.database.objects.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDO, Long> {

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);

    UserDO getUserByEmail(String email);

    UserDO getUserByUsername(String username);

    UserDO findById(long id);

    List<UserDO> findAllByUsernameLikeOrEmailLike(String username, String email);

    //TODO if columns names change, change accordingly
    @Query("SELECT t.artist_name_deezer FROM UserDO u "
            + "JOIN u.personalLibrary pl "
            + "JOIN pl.playlists p "
            + "JOIN p.tracks t "
            + "WHERE u.id = :userId "
            + "GROUP BY t.artist_name_deezer "
            + "ORDER BY COUNT(t.artist_name_deezer) DESC, t.artist_name_deezer ASC "
            + "LIMIT 1")
    String findMostFrequentArtistByUserId(Long userId);

}
