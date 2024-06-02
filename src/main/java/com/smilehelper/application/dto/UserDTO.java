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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String nickname;
    private UserRole role;
    private long coin;
    private HealthArea healthArea;
    private SeverityLevel severityLevel;
    private int age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;

    public static UserDTO build(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole())
                .coin(user.getCoin())
                .healthArea(user.getHealthArea())
                .severityLevel(user.getSeverityLevel())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isDeleted(user.isDeleted())
                .build();
    }
}
