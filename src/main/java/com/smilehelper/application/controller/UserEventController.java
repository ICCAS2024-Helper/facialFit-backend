package com.smilehelper.application.controller;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.domain.UserEvent;
import com.smilehelper.application.service.UserEventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "User Event API", description = "User Event API 목록입니다.")
@RestController
@RequestMapping("/api/user")
public class UserEventController {

    private final UserEventService userEventService;

    @Autowired
    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @PostMapping("/attendance")
    @ResponseStatus(HttpStatus.OK)
    public void recordAttendance(@RequestParam LocalDate date, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        userEventService.recordEvent(user.getUserId(), date, "ATTENDANCE");
    }

    @PostMapping("/bonus-game")
    @ResponseStatus(HttpStatus.OK)
    public void recordBonusGame(@RequestParam LocalDate date, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        userEventService.recordEvent(user.getUserId(), date, "BONUS_GAME");
    }

    @PostMapping("/story-mode")
    @ResponseStatus(HttpStatus.OK)
    public void recordStoryMode(@RequestParam LocalDate date, Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        userEventService.recordEvent(user.getUserId(), date, "STORY_MODE");
    }

    @GetMapping("/events")
    public Map<String, List<Map<String, Object>>> getUserEvents(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();
        List<UserEvent> events = userEventService.getUserEvents(user.getUserId());

        return events.stream().collect(Collectors.groupingBy(
                event -> event.getEventDate().toString(),
                Collectors.mapping(event -> Map.of("iconIndex", getIconIndex(event.getEventType())), Collectors.toList())
        ));
    }

    private int getIconIndex(String eventType) {
        switch (eventType) {
            case "ATTENDANCE":
                return 1;
            case "BONUS_GAME":
                return 2;
            case "STORY_MODE":
                return 3;
            default:
                return 0;
        }
    }
}