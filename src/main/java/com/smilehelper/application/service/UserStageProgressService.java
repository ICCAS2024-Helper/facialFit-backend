package com.smilehelper.application.service;

import com.smilehelper.application.domain.Stage;
import com.smilehelper.application.domain.User;
import com.smilehelper.application.domain.UserStageProgress;
import com.smilehelper.application.dto.UserStageProgressDTO;
import com.smilehelper.application.exception.StageException;
import com.smilehelper.application.repository.UserStageProgressRepository;
import com.smilehelper.application.repository.StageRepository;
import com.smilehelper.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserStageProgressService 클래스는 사용자 스테이지 진행 상황 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class UserStageProgressService {

    private final UserStageProgressRepository userStageProgressRepository;
    private final UserRepository userRepository;
    private final StageRepository stageRepository;

    @Autowired
    public UserStageProgressService(UserStageProgressRepository userStageProgressRepository, UserRepository userRepository, StageRepository stageRepository) {
        this.userStageProgressRepository = userStageProgressRepository;
        this.userRepository = userRepository;
        this.stageRepository = stageRepository;
    }

    @Transactional
    public void clearStage(Long userId, Long stageId) throws StageException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new StageException("스테이지를 찾을 수 없습니다."));

        UserStageProgress userStageProgress = userStageProgressRepository.findByUserIdAndStageId(userId, stageId)
                .orElse(UserStageProgress.builder()
                        .user(user)
                        .stage(stage)
                        .isCleared(false)
                        .build());

        userStageProgress.setCleared(true);

        userStageProgressRepository.save(userStageProgress);
    }

    public List<UserStageProgressDTO> getUserStageProgress(Long userId) {
        return userStageProgressRepository.findByUserId(userId).stream()
                .map(userStageProgress -> UserStageProgressDTO.builder()
                        .userId(userStageProgress.getUser().getUserId())
                        .stageId(userStageProgress.getStage().getStageId())
                        .isCleared(userStageProgress.isCleared())
                        .build())
                .collect(Collectors.toList());
    }
}