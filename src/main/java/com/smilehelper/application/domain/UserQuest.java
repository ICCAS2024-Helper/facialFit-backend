package com.smilehelper.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_quest")
public class UserQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userQuestId; // 사용자 퀘스트 식별자

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 사용자

    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest; // 퀘스트

    @Column(nullable = false)
    private LocalDateTime completedAt; // 퀘스트 완료 시간

    @PrePersist
    protected void onCreate() {
        completedAt = LocalDateTime.now(); // 퀘스트 완료 시간 설정
    }
}
