package com.smilehelper.application.service;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.domain.UserEvent;
import com.smilehelper.application.repository.UserEventRepository;
import com.smilehelper.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserEventService {

    private final UserEventRepository userEventRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserEventService(UserEventRepository userEventRepository, UserRepository userRepository) {
        this.userEventRepository = userEventRepository;
        this.userRepository = userRepository;
    }

    public void recordEvent(Long userId, LocalDate eventDate, String eventType) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(u -> {
            UserEvent userEvent = new UserEvent(null, u, eventDate, eventType);
            userEventRepository.save(userEvent);
        });
    }

    public List<UserEvent> getUserEvents(Long userId) {
        return userEventRepository.findByUserUserId(userId);
    }
}