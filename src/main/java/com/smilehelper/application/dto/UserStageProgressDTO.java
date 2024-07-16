package com.smilehelper.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserStageProgressDTO 정보
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStageProgressDTO {
    private String id; // 사용자 아이디
    private Long stageId; // 스테이지 식별자
    private boolean isCleared; // 스테이지 클리어 여부
}
