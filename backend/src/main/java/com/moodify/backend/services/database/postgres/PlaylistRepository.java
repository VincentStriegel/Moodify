package com.moodify.backend.services.database.postgres;

import com.moodify.backend.services.database.objects.PlaylistDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<PlaylistDO, Long> {

    List<PlaylistDO> findAllByTitleLike(String title);

    PlaylistDO findPlaylistDOById(long id);
}
