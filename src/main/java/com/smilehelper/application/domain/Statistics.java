package com.smilehelper.application.domain;

import com.smilehelper.application.enums.ExpressionType;
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
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statisticsId; // 통계 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 사용자 정보

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpressionType expressionType; // 표정 타입

    @Column(nullable = false)
    private int successCount; // 성공 횟수

    @Column(nullable = false)
    private int failCount; // 실패 횟수

    @Column(nullable = false)
    private int totalCount; // 총 횟수

    public Statistics(User user, ExpressionType expressionType, int successCount, int failCount, int totalCount) {
        this.user = user;
        this.expressionType = expressionType;
        this.successCount = successCount;
        this.failCount = failCount;
        this.totalCount = totalCount;
    }
}
