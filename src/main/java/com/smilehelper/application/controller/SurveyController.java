package com.smilehelper.application.controller;

import com.smilehelper.application.dto.SurveyDTO;
import com.smilehelper.application.service.SurveyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Survey API", description = "Survey API 목록입니다.")
@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public void submitSurvey(@RequestParam String id, @RequestBody SurveyDTO surveyDTO) {
        surveyService.saveSurveyResponse(id, surveyDTO);
    }
}