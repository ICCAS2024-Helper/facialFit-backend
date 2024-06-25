package com.smilehelper.application.repository;

import com.smilehelper.application.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage,Long> {
}
