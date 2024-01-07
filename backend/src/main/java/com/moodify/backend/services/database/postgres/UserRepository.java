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

    List<UserDO> findAllByUsernameLikeAndDiscographyIsNotNull(String query);

    UserDO findByIdAndDiscographyIsNotNull(long id);

    //TODO if columns names change, change accordingly
    @Query("SELECT t.artist_name FROM UserDO u "
            + "JOIN u.personalLibrary pl "
            + "JOIN pl.playlists p "
            + "JOIN p.tracks t "
            + "WHERE u.id = :userId "
            + "GROUP BY t.artist_name "
            + "ORDER BY COUNT(t.artist_name) DESC, t.artist_name ASC "
            + "LIMIT 1")
    String findMostFrequentArtistByUserId(Long userId);

}
