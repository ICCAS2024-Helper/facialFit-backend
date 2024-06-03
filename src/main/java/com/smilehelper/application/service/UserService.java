package com.smilehelper.application.service;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * UserService 클래스는 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스.
 * 사용자 정보 업데이트, 삭제 등을 처리합니다.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * UserService 생성자
     * @param userRepository 사용자 저장소
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 정보 업데이트
     * @param UserId 업데이트할 사용자 ID
     * @param updated 업데이트할 사용자 정보
     * @throws Exception 사용자 찾기 실패 시 예외 발생
     */
    @Transactional
    public void update(Long UserId, User updated) throws Exception {
        Optional<User> opUser = userRepository.findById(UserId);
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (updated.getNickname() != null)
                user.setNickname(updated.getNickname()); // 닉네임 업데이트
            if (updated.getPassword() != null)
                user.setPassword(updated.getPassword()); // 비밀번호 업데이트
            if (updated.getHealthArea() != null)
                user.setHealthArea(updated.getHealthArea()); // 아픈 부위 업데이트
            if (updated.getSeverityLevel() != null)
                user.setSeverityLevel(updated.getSeverityLevel()); // 중경도 업데이트
            if (updated.getAge() > 0)
                user.setAge(updated.getAge()); // 나이 업데이트
            user.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정
            userRepository.save(user); // 사용자 저장
        } else {
            throw new Exception("유저가 존재하지 않습니다.");
        }
    }

    /**
     * 사용자 삭제 (논리적 삭제)
     * @param UserId 삭제할 사용자 ID
     * @throws Exception 사용자 찾기 실패 시 예외 발생
     */
    @Transactional
    public void userDelete(Long UserId) throws Exception {
        Optional<User> opUser = userRepository.findById(UserId);
        if (opUser.isPresent()) {
            User user = opUser.get();
            user.setPassword("-"); // 비밀번호 초기화
            user.setDeleted(true); // 삭제 상태 설정
            user.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정
            userRepository.save(user); // 사용자 저장
        } else {
            throw new Exception("유저가 존재하지 않습니다.");
        }
    }

    /**
     * 사용자 정보 읽기
     * @param id 사용자 ID
     * @return 사용자 정보 Optional 객체
     */
    public Optional<User> read(Long id) {
        return userRepository.findById(id);
    }

}
