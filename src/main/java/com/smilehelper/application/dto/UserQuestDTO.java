package com.smilehelper.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserQuestDTO 정보
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserQuestDTO {
    private Long userQuestId;
    private Long userId;
    private Long questId;
    private LocalDateTime completedAt;
}
