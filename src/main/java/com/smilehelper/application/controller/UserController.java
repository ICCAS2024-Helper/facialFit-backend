package com.smilehelper.application.controller;

import com.smilehelper.application.domain.User;
import com.smilehelper.application.dto.UserDTO;
import com.smilehelper.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/auth")
    @ResponseBody
    public UserDTO getCurrent(@AuthenticationPrincipal User user) {
        try {
            return UserDTO.build(user);
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
     * @param id 삭제할 사용자 ID
     * @param currentUser 인증된 사용자 정보
     */
    @Operation(summary = "사용자 삭제", description = "사용자를 논리적으로 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id, @AuthenticationPrincipal User currentUser) {
        try {
            if (!Objects.equals(currentUser.getUserId(), id)) {
                throw new Exception("User not matched");
            }
            userService.userDelete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

//   아래 코드는 다시 수정할게요
//    /**
//     * 사용자 정보 조회
//     * @param userId 사용자 ID
//     * @param currentUser 인증된 사용자 정보
//     * @return UserDTO 객체
//     */
//    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "정보 조회 성공"),
//            @ApiResponse(responseCode = "404", description = "사용자 찾을 수 없음")
//    })
//    @GetMapping("/{userId}")
//    @ResponseBody
//    public UserDTO read(@PathVariable("userId") Long userId, @AuthenticationPrincipal User currentUser) {
//        try {
//            String currentUserId = currentUser.getId();
//            String currentRole = currentUser.getRole().name();
//            if (currentUserId.equals("anonymousUser") ||
//                    !currentUserId.equals(userService.read(userId).orElseThrow().getId()) ||
//                    !currentRole.equals(UserRole.ROLE_ADMIN.name())) {
//                throw new Exception("No permission");
//            }
//            return UserDTO.build(userService.read(userId).orElseThrow());
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
//        }
//    }

    /**
     * JWT 토큰 쿠키 생성
     * @param token JWT 토큰
     * @param age 쿠키 만료 시간 (초)
     * @return 생성된 쿠키 객체
     */
    private Cookie createTokenCookie(String token, int age) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(age);
        cookie.setPath("/");
        return cookie;
    }
}
