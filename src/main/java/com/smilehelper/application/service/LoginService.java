package com.smilehelper.application.service;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.dto.LoginDTO;
import com.smilehelper.application.exception.UserException;
import com.smilehelper.application.repository.UserRepository;
import com.smilehelper.application.security.JwtTokenProvider;
import com.smilehelper.application.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * LoginService 클래스는 사용자 로그인 관련 비즈니스 로직을 처리하는 서비스 클래스.
 */
@Service
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * LoginService 생성자
     * @param userRepository 사용자 저장소
     * @param passwordEncoder 비밀번호 인코더
     */
    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 로그인 시도
     * @param loginDTO 로그인 정보 DTO
     * @return JWT 토큰
     * @throws UsernameNotFoundException 사용자 이름이 없거나 상태가 비활성화된 경우 예외 발생
     */
    public String tryLogin(LoginDTO loginDTO) throws UsernameNotFoundException {
        User user = userRepository.findById(loginDTO.getId())
                .orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));

        if (user.isDeleted())
            throw new UsernameNotFoundException("유저가 존재하지 않습니다.");

        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            Authentication authentication = new UserAuthentication(
                    user, loginDTO.getPassword(), user.getAuthorities()
            );
            return JwtTokenProvider.generateToken(authentication); // JWT 토큰 생성
        } else {
            throw new UserException("패스워드가 일치하지 않습니다.");
        }
    }

    /**
     * 사용자 이름으로 사용자 정보 로드
     * @param id 사용자 이름
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 사용자 이름이 없는 경우 예외 발생
     */
    @Override
    public User loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));
    }
}
