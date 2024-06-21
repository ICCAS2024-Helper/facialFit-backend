package com.smilehelper.application.dto;

import com.smilehelper.application.enums.QuestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * QuestDTO 정보
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestDTO {
    private String title;
    private String content;
    private QuestType type;
    private LocalDateTime nextAvailableTime;
}
