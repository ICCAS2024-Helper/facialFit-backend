package com.smilehelper.application.security;

import com.smilehelper.application.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * UserAuthentication 클래스는 사용자 인증 정보를 저장합니다.
 */
public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(User principal, String credentials) {
        super(principal, credentials);
    }

    public UserAuthentication(User principal, String credentials,
                              Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
