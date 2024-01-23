package com.moodify.backend.services.database.postgres;

import com.moodify.backend.services.database.objects.MoodifySingleDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This interface extends JpaRepository and represents the repository for MoodifySingleDO objects.
 * It provides methods for performing CRUD operations on the MoodifySingleDO objects in the database.
 * The JpaRepository interface provides methods for general CRUD operations,
 * and the MoodifySingleRepository interface provides additional methods specific to MoodifySingleDO objects.
 */
public interface MoodifySingleRepository extends JpaRepository<MoodifySingleDO, Long> {
    List<MoodifySingleDO> findAllByTitleLike(String title);

    MoodifySingleDO findMoodifySingleDOById(long id);
}
