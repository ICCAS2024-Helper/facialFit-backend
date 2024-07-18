package com.smilehelper.application.dto;

import com.smilehelper.application.enums.ExpressionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    private ExpressionType expressionType;
    private int successCount;
    private int failCount;
}


