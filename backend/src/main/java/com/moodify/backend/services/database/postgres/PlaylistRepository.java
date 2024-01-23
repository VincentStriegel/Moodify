package com.moodify.backend.services.database.postgres;

import com.moodify.backend.services.database.objects.PlaylistDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This interface extends JpaRepository and represents the repository for PlaylistDO objects.
 * It provides methods for performing CRUD operations on the PlaylistDO objects in the database.
 * The JpaRepository interface provides methods for general CRUD operations,
 * and the PlaylistRepository interface provides additional methods specific to PlaylistDO objects.
 */
public interface PlaylistRepository extends JpaRepository<PlaylistDO, Long> {

    List<PlaylistDO> findAllByTitleLike(String title);

    PlaylistDO findPlaylistDOById(long id);

}
