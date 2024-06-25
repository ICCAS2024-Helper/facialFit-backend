package com.smilehelper.application.controller;

import com.smilehelper.application.dto.UserStageProgressDTO;
import com.smilehelper.application.exception.StageException;
import com.smilehelper.application.service.UserStageProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserStageProgressController 클래스는 사용자 스테이지 진행 상황 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "UserStageProgress API", description = "UserStageProgress API 목록입니다.")
@RestController
@RequestMapping("/api/users/{userId}/stages")
public class UserStageProgressController {

    private final UserStageProgressService userStageProgressService;

    @Autowired
    public UserStageProgressController(UserStageProgressService userStageProgressService) {
        this.userStageProgressService = userStageProgressService;
    }

    @Operation(summary = "사용자의 스테이지 진행 상황 조회", description = "사용자가 진행한 스테이지의 진행 상황을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/progress")
    public ResponseEntity<List<UserStageProgressDTO>> getUserStageProgress(@PathVariable Long userId) {
        List<UserStageProgressDTO> progress = userStageProgressService.getUserStageProgress(userId);
        return ResponseEntity.ok(progress);
    }

    @Operation(summary = "스테이지 완료 처리", description = "사용자가 스테이지를 완료합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "완료 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "스테이지를 찾을 수 없음")
    })
    @PostMapping("/complete/{stageId}")
    public ResponseEntity<Void> completeStage(@PathVariable Long userId, @PathVariable Long stageId) {
        try {
            userStageProgressService.clearStage(userId, stageId);
            return ResponseEntity.ok().build();
        } catch (StageException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}