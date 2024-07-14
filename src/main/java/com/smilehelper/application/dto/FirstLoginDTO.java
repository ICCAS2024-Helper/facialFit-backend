package com.smilehelper.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirstLoginDTO {
    private boolean firstLogin; // 첫 로그인 여부
}
