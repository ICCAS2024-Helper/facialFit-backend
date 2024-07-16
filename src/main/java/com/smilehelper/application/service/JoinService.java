package com.smilehelper.application.service;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.dto.JoinDTO;
import com.smilehelper.application.enums.UserRole;
import com.smilehelper.application.exception.UserException;
import com.smilehelper.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * JoinService 클래스는 사용자 가입 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class JoinService {

    // 비밀번호 규칙 정규 표현식 (최소 8자, 문자와 숫자 포함)
    private static final String PASSWORD_RULE = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d@$!%*#?&]{8,}$";

    private final UserRepository userRepository; // 사용자 저장소
    private final PasswordEncoder passwordEncoder; // 비밀번호 인코더

    /**
     * JoinService 생성자
     * @param userRepository 사용자 저장소
     * @param passwordEncoder 비밀번호 인코더
     */
    @Autowired
    public JoinService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 새로운 사용자 생성
     * @param joinDTO 새로운 사용자 정보
     * @throws UserException 사용자 이름 중복 시 예외 발생
     */
    @Transactional
    public void create(JoinDTO joinDTO) throws UserException {
        if (userRepository.existsById(joinDTO.getId()) || joinDTO.getId().equals("익명의 사용자")) {
            throw new UserException("유저의 아이디가 중복되었습니다.");
        }
//        if (!Pattern.matches(PASSWORD_RULE, joinDTO.getPassword())) {
//            throw new UserException("비밀번호 규칙이 일치하지 않습니다.");
//        }
        User user = User.builder()
                .id(joinDTO.getId())
                .nickname(joinDTO.getNickname())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .role(UserRole.ROLE_USER)
                .coin(0) // 코인 기본값 0으로 설정
                .healthArea(joinDTO.getHealthArea())
                .severityLevel(joinDTO.getSeverityLevel())
                .age(joinDTO.getAge())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .surveyQuestion1(joinDTO.getSurveyQuestion1())
                .surveyQuestion2(joinDTO.getSurveyQuestion2())
                .surveyQuestion3(joinDTO.getSurveyQuestion3())
                .surveyQuestion4(joinDTO.getSurveyQuestion4())
                .surveyQuestion5(joinDTO.getSurveyQuestion5())
                .surveyQuestion6(joinDTO.getSurveyQuestion6())
                .isDeleted(false)
                .firstLogin(true)
                .firstPhoto(true)
                .build();
        userRepository.save(user);
    }

    /**
     * 아이디 중복 확인
     * @param id 아이디
     * @return 중복 여부
     */
    public Boolean canUseAsId(String id) {
        return !userRepository.existsById(id);
    }
}
