package kr.ac.hufs.ice.ice.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.hufs.ice.ice.service.TokenService;
import kr.ac.hufs.ice.ice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogoutController {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if (token != null && jwtUtil.validateToken(token)) {
            // 남은 만료시간 계산 (현재시간 - 토큰 만료시간)
            long expirationMillis = JwtUtil.ACCESS_TOKEN_EXP - (System.currentTimeMillis() - jwtUtil.getIssuedAt(token));
            if (expirationMillis > 0) {
                tokenService.blacklistAccessToken(token, expirationMillis); // 만료시간 만큼만 블랙리스트
                tokenService.deleteRefreshToken(jwtUtil.getStudentIdFromToken(token));
            }

            // 쿠키 만료시키기
            ResponseCookie expiredCookie = ResponseCookie.from("accessToken", "")
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(0)
                    .build();
            response.setHeader("Set-Cookie", expiredCookie.toString());

            return ResponseEntity.ok().body("로그아웃 성공");
        } else {
            return ResponseEntity.badRequest().body("유효한 토큰이 없습니다.");
        }
    }
}
