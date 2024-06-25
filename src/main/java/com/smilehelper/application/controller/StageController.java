package com.smilehelper.application.controller;

import com.smilehelper.application.dto.StageDTO;
import com.smilehelper.application.service.StageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StageController 클래스는 스테이지 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "Stage API", description = "Stage API 목록입니다.")
@RestController
@RequestMapping("/api/stages")
public class StageController {

    private final StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @Operation(summary = "스테이지 목록 조회", description = "스테이지 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<StageDTO>> getAllStages() {
        List<StageDTO> stages = stageService.getAllStages();
        return ResponseEntity.ok(stages);
    }

    @Operation(summary = "스테이지 생성", description = "스테이지를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공")
    })
    @PostMapping
    public ResponseEntity<StageDTO> createStage(@RequestBody StageDTO stageDTO) {
        StageDTO createdStage = stageService.createStage(stageDTO);
        return ResponseEntity.status(201).body(createdStage);
    }

    @Operation(summary = "스테이지 업데이트", description = "스테이지를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공")
    })
    @PutMapping("/{stageId}")
    public ResponseEntity<Void> updateStage(@PathVariable Long stageId, @RequestBody StageDTO stageDTO) {
        try {
            stageService.updateStage(stageId, stageDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @Operation(summary = "스테이지 삭제", description = "스테이지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공")
    })
    @DeleteMapping("/{stageId}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long stageId) {
        try {
            stageService.deleteStage(stageId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}