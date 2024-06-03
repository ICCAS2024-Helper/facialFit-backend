package com.smilehelper.application.dto;

import com.smilehelper.application.enums.HealthArea;
import com.smilehelper.application.enums.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자가 회원가입 시 입력해야할 정보.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinDTO {
    private String id;          // 로그인에 사용되는 아이디
    private String nickname;    // 사용자의 닉네임
    private String password;    // 비밀번호
    private int age;            // 나이
    private HealthArea healthArea;  // 아픈 부위
    private SeverityLevel severityLevel; // 중경도
}