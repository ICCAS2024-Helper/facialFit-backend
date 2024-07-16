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
    public void clearStage(String id, Long stageId) throws StageException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new StageException("스테이지를 찾을 수 없습니다."));

        // 이전 스테이지를 클리어했는지 확인
        if (stage.getStageNumber() > 1) {
            Long previousStageId = stageRepository.findByStageNumber(stage.getStageNumber() - 1)
                    .orElseThrow(() -> new StageException("이전 스테이지를 찾을 수 없습니다.")).getStageId();
            UserStageProgress previousStageProgress = userStageProgressRepository.findByUserIdAndStageId(id, previousStageId)
                    .orElseThrow(() -> new StageException("이전 스테이지를 클리어하지 않았습니다."));
            if (!previousStageProgress.isCleared()) {
                throw new StageException("이전 스테이지를 클리어하지 않았습니다.");
            }
        }

        UserStageProgress userStageProgress = userStageProgressRepository.findByUserIdAndStageId(id, stageId)
                .orElse(UserStageProgress.builder()
                        .user(user)
                        .stage(stage)
                        .isCleared(false)
                        .build());

        userStageProgress.setCleared(true);

        userStageProgressRepository.save(userStageProgress);
    }

    public List<UserStageProgressDTO> getUserStageProgress(String id) {
        return userStageProgressRepository.findByUserId(id).stream()
                .map(userStageProgress -> UserStageProgressDTO.builder()
                        .id(userStageProgress.getUser().getId())
                        .stageId(userStageProgress.getStage().getStageId())
                        .isCleared(userStageProgress.isCleared())
                        .build())
                .collect(Collectors.toList());
    }
}