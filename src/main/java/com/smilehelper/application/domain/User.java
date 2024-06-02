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
    private Long id;

    @Column(unique = true, nullable = false)
    @NotEmpty
    @Size(min = 4, max = 25)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "유저이름은 영문 대소문자와 숫자만 사용할 수 있습니다.")
    private String username; // 로그인에 사용되는 아이디

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 4, max = 100)
    private String nickname; // 사용자의 닉네임

    @Column(nullable = false)
    @NotEmpty
    @Size(min = 4, max = 100)
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

    @Column(nullable = false)
    private int age; // 나이

    @Column(nullable = false)
    private LocalDateTime createdAt; // 가입일

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 정보수정일

    @Column(nullable = false)
    private boolean isDeleted; // 탈퇴여부

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
}
