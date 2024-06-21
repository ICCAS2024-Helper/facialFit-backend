package com.smilehelper.application.service;

import com.smilehelper.application.domain.Quest;
import com.smilehelper.application.domain.User;
import com.smilehelper.application.domain.UserQuest;
import com.smilehelper.application.dto.QuestDTO;
import com.smilehelper.application.repository.UserQuestRepository;
import com.smilehelper.application.repository.UserRepository;
import com.smilehelper.application.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserQuestService 클래스는 사용자 퀘스트 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class UserQuestService {

    private final UserQuestRepository userQuestRepository;
    private final UserRepository userRepository;
    private final QuestRepository questRepository;

    @Autowired
    public UserQuestService(UserQuestRepository userQuestRepository, UserRepository userRepository, QuestRepository questRepository) {
        this.userQuestRepository = userQuestRepository;
        this.userRepository = userRepository;
        this.questRepository = questRepository;
    }

    @Transactional
    public void completeQuest(Long userId, Long questId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Quest quest = questRepository.findById(questId).orElseThrow(() -> new RuntimeException("Quest not found"));

        UserQuest userQuest = UserQuest.builder()
                .user(user)
                .quest(quest)
                .completedAt(LocalDateTime.now())
                .build();

        userQuestRepository.save(userQuest);
    }

    public List<QuestDTO> getIncompleteQuests(Long userId) {
        List<Long> completedQuestIds = userQuestRepository.findByUserId(userId).stream()
                .map(userQuest -> userQuest.getQuest().getQuestId())
                .collect(Collectors.toList());

        return questRepository.findAll().stream()
                .filter(quest -> !completedQuestIds.contains(quest.getQuestId()))
                .map(quest -> QuestDTO.builder()
                        .title(quest.getTitle())
                        .content(quest.getContent())
                        .type(quest.getType())
                        .nextAvailableTime(quest.getNextAvailableTime())
                        .build())
                .collect(Collectors.toList());
    }

    public List<QuestDTO> getUserCompletedQuests(Long userId) {
        return userQuestRepository.findByUserId(userId).stream()
                .map(userQuest -> {
                    Quest quest = userQuest.getQuest();
                    return QuestDTO.builder()
                            .title(quest.getTitle())
                            .content(quest.getContent())
                            .type(quest.getType())
                            .nextAvailableTime(quest.getNextAvailableTime())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
