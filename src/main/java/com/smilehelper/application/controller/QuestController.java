package com.smilehelper.application.controller;

import com.smilehelper.application.dto.QuestDTO;
import com.smilehelper.application.service.QuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * QuestController 클래스는 퀘스트 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "Quest API", description = "Quest API 목록입니다.")
@RestController
@RequestMapping("/api/quests")
public class QuestController {

    private final QuestService questService;

    /**
     * QuestController 생성자
     * @param questService 퀘스트 서비스
     */
    @Autowired
    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    /**
     * 퀘스트 목록 조회
     * @return 퀘스트 목록
     */
    @Operation(summary = "퀘스트 목록 조회", description = "퀘스트 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<QuestDTO>> getAllQuests() {
        List<QuestDTO> quests = questService.getAllQuests();
        return ResponseEntity.ok(quests);
    }

    @Operation(summary = "퀘스트 조회", description = "퀘스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/{questId}")
    public ResponseEntity<QuestDTO> getQuest(@PathVariable Long questId) {
        QuestDTO quest = questService.getQuest(questId);
        return ResponseEntity.ok(quest);
    }

    @Operation(summary = "퀘스트 생성", description = "퀘스트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공")
    })
    @PostMapping
    public ResponseEntity<QuestDTO> createQuest(@RequestBody QuestDTO questDTO) {
        QuestDTO createdQuest = questService.createQuest(questDTO);
        return ResponseEntity.ok(createdQuest);
    }

    @Operation(summary = "퀘스트 업데이트", description = "퀘스트를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공")
    })
    @PutMapping("/{questId}")
    public ResponseEntity<QuestDTO> updateQuest(@PathVariable Long questId, @RequestBody QuestDTO questDTO) {
        questService.updateQuest(questId, questDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "퀘스트 삭제", description = "퀘스트를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공")
    })
    @DeleteMapping("/{questId}")
    public ResponseEntity<Void> deleteQuest(@PathVariable Long questId) {
        questService.deleteQuest(questId);
        return ResponseEntity.noContent().build();
    }
}
