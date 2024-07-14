package com.smilehelper.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyDTO {
    private Boolean subwayQuestion1; // 설문조사1
    private Boolean subwayQuestion2; // 설문조사2
    private Boolean subwayQuestion3; // 설문조사3
    private Boolean subwayQuestion4; // 설문조사4
    private Boolean subwayQuestion5; // 설문조사5
    private Boolean subwayQuestion6; // 설문조사6
}
