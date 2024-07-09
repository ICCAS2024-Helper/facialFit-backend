package com.smilehelper.application.controller;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.dto.UserCoinDTO;
import com.smilehelper.application.dto.UserDTO;
import com.smilehelper.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * UserController 클래스는 사용자 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "User API", description = "User API 목록입니다.")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * UserController 생성자
     * @param userService 사용자 서비스
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 현재 사용자 정보 조회
     * @param user 인증된 사용자 정보
     * @return UserDTO 객체
     */
    @Operation(summary = "현재 사용자 정보 조회", description = "인증된 현재 사용자 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping("/all")
    @ResponseBody
    public UserDTO getCurrent(@AuthenticationPrincipal User user) {
        try {
            return UserDTO.build(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "사용자의 코인 정보 조회", description = "현재 사용자의 코인 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping("/coin")
    public ResponseEntity<UserCoinDTO> getUserCoin(@AuthenticationPrincipal User user) {
        try {
            UserCoinDTO userCoinDTO = userService.getUserCoin(user.getUserId());
            return ResponseEntity.ok(userCoinDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    /**
     * 사용자 정보 업데이트
     * @param userId 업데이트할 사용자 ID
     * @param user 업데이트할 사용자 정보
     * @param currentUser 인증된 사용자 정보
     */
    @Operation(summary = "사용자 정보 업데이트", description = "사용자 정보를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PutMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void update(@PathVariable("userId") Long userId, @RequestBody User user, @AuthenticationPrincipal User currentUser) {
        try {
            if (!Objects.equals(currentUser.getUserId(), userId)) {
                throw new Exception("User not matched");
            }
            userService.update(userId, user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * 사용자 삭제 (논리적 삭제)
     * @param UserId 삭제할 사용자 ID
     * @param currentUser 인증된 사용자 정보
     */
    @Operation(summary = "사용자 삭제", description = "사용자를 논리적으로 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @DeleteMapping("/{UserId}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable("UserId") Long UserId, @AuthenticationPrincipal User currentUser) {
        try {
            if (!Objects.equals(currentUser.getUserId(), UserId)) {
                throw new Exception("User not matched");
            }
            userService.userDelete(UserId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
