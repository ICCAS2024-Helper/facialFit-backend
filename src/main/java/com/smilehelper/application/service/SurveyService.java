package com.smilehelper.application.service;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.dto.SurveyDTO;
import com.smilehelper.application.exception.UserException;
import com.smilehelper.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurveyService {

    private final UserRepository userRepository;

    @Autowired
    public SurveyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveSurveyResponse(String id, SurveyDTO surveyDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSurveyQuestion1(surveyDTO.getSubwayQuestion1());
            user.setSurveyQuestion2(surveyDTO.getSubwayQuestion2());
            user.setSurveyQuestion3(surveyDTO.getSubwayQuestion3());
            user.setSurveyQuestion4(surveyDTO.getSubwayQuestion4());
            user.setSurveyQuestion5(surveyDTO.getSubwayQuestion5());
            user.setSurveyQuestion6(surveyDTO.getSubwayQuestion6());
            userRepository.save(user);
        } else {
            throw new UserException("사용자를 찾을 수 없습니다.");
        }
    }
}