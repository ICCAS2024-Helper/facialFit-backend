package com.smilehelper.application.repository;

import com.smilehelper.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id); //id로 사용자 찾기
    boolean existsById(String id); //id 중복 확인
}
