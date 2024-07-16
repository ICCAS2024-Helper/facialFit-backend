package com.smilehelper.application.repository;

import com.smilehelper.application.domain.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
    List<UserEvent> findByUserUserId(Long userId);
}