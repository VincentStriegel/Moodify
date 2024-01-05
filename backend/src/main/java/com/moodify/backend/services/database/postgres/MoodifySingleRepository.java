package com.moodify.backend.services.database.postgres;

import com.moodify.backend.services.database.objects.MoodifySingleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodifySingleRepository extends JpaRepository<MoodifySingleDO, Long> {
    List<MoodifySingleDO> findAllByTitleLike(String title);

    MoodifySingleDO findMoodifySingleDOById(long id);
}
