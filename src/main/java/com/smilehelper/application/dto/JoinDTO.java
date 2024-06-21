package com.smilehelper.application.dto;

import com.smilehelper.application.enums.HealthArea;
import com.smilehelper.application.enums.SeverityLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자가 회원가입 시 입력해야할 정보.
 * 패스워드 유효성 검증은 서비스 레벨에서 처리
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