package com.smilehelper.application.service;

import com.smilehelper.application.domain.Stage;
import com.smilehelper.application.dto.StageDTO;
import com.smilehelper.application.exception.StageException;
import com.smilehelper.application.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StageService 클래스는 스테이지 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class StageService {

    private final StageRepository stageRepository;

    @Autowired
    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @Transactional
    public StageDTO createStage(StageDTO stageDTO) {
        Stage stage = Stage.builder()
                .stageNumber(stageDTO.getStageNumber())
                .build();
        Stage savedStage = stageRepository.save(stage);
        return StageDTO.builder()
                .stageId(savedStage.getStageId())
                .stageNumber(savedStage.getStageNumber())
                .build();
    }

    @Transactional
    public void updateStage(Long stageId, StageDTO stageDTO) throws StageException {
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new StageException("스테이지를 찾을 수 없습니다."));
        if (stageDTO.getStageNumber() != 0) stage.setStageNumber(stageDTO.getStageNumber());
        stageRepository.save(stage);
    }

    @Transactional
    public void deleteStage(Long stageId) throws StageException {
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new StageException("스테이지를 찾을 수 없습니다."));
        stageRepository.delete(stage);
    }

    public List<StageDTO> getAllStages() {
        return stageRepository.findAll().stream()
                .map(stage -> StageDTO.builder()
                        .stageId(stage.getStageId())
                        .stageNumber(stage.getStageNumber())
                        .build())
                .collect(Collectors.toList());
    }
}