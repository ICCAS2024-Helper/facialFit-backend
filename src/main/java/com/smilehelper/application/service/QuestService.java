package com.smilehelper.application.service;

import com.smilehelper.application.domain.Quest;
import com.smilehelper.application.dto.QuestDTO;
import com.smilehelper.application.exception.QuestException;
import com.smilehelper.application.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * QuestService 클래스는 퀘스트 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class QuestService {

    private final QuestRepository questRepository;

    @Autowired
    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @Transactional
    public QuestDTO createQuest(QuestDTO questDTO) {
        Quest quest = Quest.builder()
                .title(questDTO.getTitle())
                .content(questDTO.getContent())
                .type(questDTO.getType())
                .nextAvailableTime(questDTO.getNextAvailableTime())
                .build();
        Quest savedQuest = questRepository.save(quest);
        return QuestDTO.builder()
                .title(savedQuest.getTitle())
                .content(savedQuest.getContent())
                .type(savedQuest.getType())
                .nextAvailableTime(savedQuest.getNextAvailableTime())
                .build();
    }

    @Transactional
    public void updateQuest(Long questId, QuestDTO questDTO) throws QuestException {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new QuestException("퀘스트를 찾을 수 없습니다."));
        if (questDTO.getTitle() != null) quest.setTitle(questDTO.getTitle());
        if (questDTO.getContent() != null) quest.setContent(questDTO.getContent());
        if (questDTO.getType() != null) quest.setType(questDTO.getType());
        if (questDTO.getNextAvailableTime() != null) quest.setNextAvailableTime(questDTO.getNextAvailableTime());
        questRepository.save(quest);
    }

    @Transactional
    public void deleteQuest(Long questId) throws QuestException {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new QuestException("퀘스트를 찾을 수 없습니다."));
        questRepository.delete(quest);
    }

    public QuestDTO getQuest(Long questId) throws QuestException {
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new QuestException("퀘스트를 찾을 수 없습니다."));
        return QuestDTO.builder()
                .title(quest.getTitle())
                .content(quest.getContent())
                .type(quest.getType())
                .nextAvailableTime(quest.getNextAvailableTime())
                .build();
    }

    public List<QuestDTO> getAllQuests() {
        return questRepository.findAll().stream()
                .map(quest -> QuestDTO.builder()
                        .title(quest.getTitle())
                        .content(quest.getContent())
                        .type(quest.getType())
                        .nextAvailableTime(quest.getNextAvailableTime())
                        .build())
                .collect(Collectors.toList());
    }
}
