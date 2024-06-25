package com.smilehelper.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_stage_progress")
public class UserStageProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userStageProgressId; // 사용자 스테이지 진행 식별자

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 사용자

    @ManyToOne
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage; // 스테이지

    @Column(nullable = false)
    private boolean isCleared; // 스테이지 완료 여부

}
