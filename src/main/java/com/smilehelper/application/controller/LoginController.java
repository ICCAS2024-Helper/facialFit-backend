package com.smilehelper.application.controller;

import com.smilehelper.application.dto.LoginDTO;
import com.smilehelper.application.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * LoginController 클래스는 로그인 관련 HTTP 요청을 처리합니다.
 */
@Tag(name = "Login API", description = "Login API 목록입니다.")
@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    /**
     * LoginController 생성자
     * @param loginService 로그인 서비스
     */
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 사용자 로그인 처리
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param loginDTO 로그인 정보 DTO
     */
    @Operation(summary = "로그인", description = "사용자 로그인을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public void login(final HttpServletRequest request, final HttpServletResponse response,
                      @RequestBody LoginDTO loginDTO) {
        try {
            String token = loginService.tryLogin(loginDTO);
            Cookie tokenCookie = createTokenCookie(token, 168 * 60 * 60);
            response.addCookie(tokenCookie);
        } catch (Exception e) {
            Cookie emptyCookie = createTokenCookie(null, 0);
            response.addCookie(emptyCookie);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * 사용자 로그아웃 처리
     * @param request HTTP 요청
     * @param response HTTP 응답
     */
    @Operation(summary = "로그아웃", description = "사용자 로그아웃을 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/logout")
    @ResponseStatus(code = HttpStatus.OK)
    public void logout(final HttpServletRequest request, final HttpServletResponse response) {
        Cookie tokenCookie = createTokenCookie(null, 0);
        response.addCookie(tokenCookie);
    }

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
