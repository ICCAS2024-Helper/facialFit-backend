package com.smilehelper.application.controller;

import com.smilehelper.application.dto.StatisticsDTO;
import com.smilehelper.application.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Statistics API", description = "Statistics API 목록입니다.")
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Operation(summary = "게임 결과 통계 저장", description = "게임 결과 통계를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "저장 성공")
    })
    @PostMapping("/save")
    public ResponseEntity<Void> saveStatistics(@RequestParam String id, @RequestBody List<StatisticsDTO> statisticsDTOs) {
        statisticsService.saveStatistics(id, statisticsDTOs);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자별 통계 조회", description = "사용자별 통계 결과를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<StatisticsDTO>> getStatisticsByUser(@PathVariable String id) {
        List<StatisticsDTO> statistics = statisticsService.getStatisticsByUser(id);
        if (statistics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statistics);
    }
}