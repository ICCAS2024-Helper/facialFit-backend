package com.smilehelper.application.repository;

import com.smilehelper.application.domain.UserStageProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStageProgressRepository extends JpaRepository<UserStageProgress, Long> {
    @Query("SELECT usp FROM UserStageProgress usp WHERE usp.user.userId = :userId")
    List<UserStageProgress> findByUserId(@Param("userId") Long userId);

    @Query("SELECT usp FROM UserStageProgress usp WHERE usp.user.userId = :userId AND usp.stage.stageId = :stageId")
    Optional<UserStageProgress> findByUserIdAndStageId(@Param("userId") Long userId, @Param("stageId") Long stageId);
}