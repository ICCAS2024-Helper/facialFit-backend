package com.smilehelper.application.controller;

import com.smilehelper.application.dto.JoinDTO;
import com.smilehelper.application.service.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * JoinController 클래스는 회원가입 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "Join API", description = "Join API 목록입니다.")
@RestController
@RequestMapping("/api/join")
public class JoinController {

    private final JoinService joinService;

    /**
     * JoinController 생성자
     * @param joinService 회원가입 서비스
     */
    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    /**
     * 새로운 사용자 생성
     * @param joinDTO 새로운 사용자 객체
     */
    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/join")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void create(@RequestBody JoinDTO joinDTO) {
        try {
            joinService.create(joinDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * 아이디 중복 확인
     * @param id 아이디
     * @return 중복 여부
     */
    @Operation(summary = "아이디 중복 확인", description = "아이디 중복 여부를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용 가능",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
    @GetMapping("/check/id")
    @ResponseBody
    public Boolean canUseAsId(@RequestParam("id") String id) {
        try {
            return joinService.canUseAsId(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
