package com.smilehelper.application.dto;

import lombok.Data;

/**
 * 사용자가 로그인 시 입력해야할 정보.
 */
@Data
public class LoginDTO {
    private String id;
    private String password;
}
