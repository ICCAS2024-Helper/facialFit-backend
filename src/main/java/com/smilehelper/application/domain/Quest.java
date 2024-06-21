package com.smilehelper.application.domain;

import com.smilehelper.application.enums.QuestType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quest")
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questId; // 퀘스트 식별자

    @Column(nullable = false)
    @NotEmpty
    private String title; // 퀘스트 제목

    @Column(nullable = false)
    @NotEmpty
    private String content; // 퀘스트 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestType type; // 퀘스트 타입

    @Column
    private LocalDateTime nextAvailableTime; // 다음 퀘스트를 받을 수 있는 시간

    @OneToMany(mappedBy = "quest")
    private List<UserQuest> userQuests; // 사용자 퀘스트 목록

    @PrePersist
    protected void onCreate() {
        nextAvailableTime = LocalDateTime.now(); // 초기화 시점 설정
    }

    @PreUpdate
    protected void onUpdate() {
        nextAvailableTime = LocalDateTime.now(); // 업데이트 시점 설정
    }

}
