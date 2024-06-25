package com.smilehelper.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StageDTO 정보
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StageDTO {
    private Long stageId; // 스테이지 식별자
    private int stageNumber; // 스테이지 번호
}
