package com.smilehelper.application.controller;

import com.smilehelper.application.dto.QuestDTO;
import com.smilehelper.application.service.UserQuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserQuestController 클래스는 사용자 퀘스트 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "UserQuest API", description = "UserQuest API 목록입니다.")
@RestController
@RequestMapping("/api/users/{userId}/quests")
public class UserQuestController {

    private final UserQuestService userQuestService;

    @Autowired
    public UserQuestController(UserQuestService userQuestService) {
        this.userQuestService = userQuestService;
    }

    @Operation(summary = "완료한 퀘스트 목록 조회", description = "사용자가 완료한 퀘스트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/completed")
    public ResponseEntity<List<QuestDTO>> getCompletedQuests(@PathVariable Long userId) {
        List<QuestDTO> completedQuests = userQuestService.getUserCompletedQuests(userId);
        return ResponseEntity.ok(completedQuests);
    }

    @Operation(summary = "완료하지 않은 퀘스트 목록 조회", description = "사용자가 완료하지 않은 퀘스트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/incomplete")
    public ResponseEntity<List<QuestDTO>> getIncompleteQuests(@PathVariable Long userId) {
        List<QuestDTO> incompleteQuests = userQuestService.getIncompleteQuests(userId);
        return ResponseEntity.ok(incompleteQuests);
    }

    @Operation(summary = "퀘스트 완료 처리", description = "사용자가 퀘스트를 완료합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "완료 성공")
    })
    @PostMapping("/complete/{questId}")
    public ResponseEntity<Void> completeQuest(@PathVariable Long userId, @PathVariable Long questId) {
        userQuestService.completeQuest(userId, questId);
        return ResponseEntity.ok().build();
    }
}
