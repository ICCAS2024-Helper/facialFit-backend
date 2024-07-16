package com.smilehelper.application.repository;

import com.smilehelper.application.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage,Long> {
    Optional<Stage> findByStageNumber(int stageNumber);
}