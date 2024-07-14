package com.smilehelper.application.domain;

import com.smilehelper.application.enums.HealthArea;
import com.smilehelper.application.enums.SeverityLevel;
import com.smilehelper.application.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // 사용자 식별자

    @Column(unique = true, nullable = false)
    @NotEmpty
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문 대소문자와 숫자만 사용할 수 있습니다.")
    private String id; // 로그인에 사용되는 아이디

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 4, max = 20)
    private String nickname; // 사용자의 닉네임

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 4, max = 20)
    private String password; // 비밀번호 (암호화되어 저장)

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role; // 사용자의 권한

    @Column(nullable = false)
    private long coin; // 사용자의 코인

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private HealthArea healthArea; // 아픈 부위

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SeverityLevel severityLevel; // 중경도

    @Column(nullable = true)
    private Boolean surveyQuestion1;

    @Column(nullable = true)
    private Boolean surveyQuestion2;

    @Column(nullable = true)
    private Boolean surveyQuestion3;

    @Column(nullable = true)
    private Boolean surveyQuestion4;

    @Column(nullable = true)
    private Boolean surveyQuestion5;

    @Column(nullable = true)
    private Boolean surveyQuestion6;

    @Column(nullable = false)
    private int age; // 나이

    @Column(nullable = false)
    private boolean firstLogin; // 첫 로그인 여부

    @Column(nullable = false)
    private LocalDateTime createdAt; // 가입일

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 정보수정일

    @Column(nullable = false)
    private boolean isDeleted; // 탈퇴여부

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "user")
    private List<UserQuest> userQuests; // 사용자의 퀘스트 목록

    // UserDetails 인터페이스 구현 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted;
    }

    @Override
    public String getUsername() {
        return id; // getId 메서드 추가
    }
}
