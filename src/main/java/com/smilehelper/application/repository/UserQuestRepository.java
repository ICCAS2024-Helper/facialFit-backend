package com.smilehelper.application.repository;

import com.smilehelper.application.domain.UserQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {
    @Query("SELECT uq FROM UserQuest uq WHERE uq.user.userId = :userId")
    List<UserQuest> findByUserId(@Param("userId") Long userId);
}
