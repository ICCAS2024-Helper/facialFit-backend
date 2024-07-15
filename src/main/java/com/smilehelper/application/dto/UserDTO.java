package com.smilehelper.application.dto;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.enums.HealthArea;
import com.smilehelper.application.enums.SeverityLevel;
import com.smilehelper.application.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * UserDB 정보.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String id;
    private String nickname;
    private UserRole role;
    private long coin;
    private HealthArea healthArea;
    private SeverityLevel severityLevel;
    private int age;

    public static UserDTO build(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .id(user.getId())
                .nickname(user.getNickname())
                .role(user.getRole())
                .coin(user.getCoin())
                .healthArea(user.getHealthArea())
                .severityLevel(user.getSeverityLevel())
                .age(user.getAge())
                .build();
    }
}
