package kr.ac.hufs.ice.ice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.hufs.ice.ice.service.TokenService;
import kr.ac.hufs.ice.ice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("refreshToken이 없습니다.");
        }

        boolean validRefresh = jwtUtil.validateToken(refreshToken);
        if (!validRefresh) {
            return ResponseEntity.status(401).body("유효하지 않은 refreshToken입니다.");
        }

        String studentId = jwtUtil.getStudentIdFromToken(refreshToken);
        String savedRefreshToken = tokenService.getRefreshToken(studentId);
        if (savedRefreshToken == null || !savedRefreshToken.equals(refreshToken)) {
            return ResponseEntity.status(401).body("저장된 refreshToken과 일치하지 않습니다.");
        }

        String newAccessToken = jwtUtil.generateAccessToken(studentId, null); // Role이 필요하면 수정 필요

        ResponseCookie cookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Lax")
                .build();
        response.setHeader("Set-Cookie", cookie.toString());

        Map<String, String> body = new HashMap<>();
        body.put("accessToken", newAccessToken);
        body.put("message", "AccessToken 재발급 성공");

        return ResponseEntity.ok(body);
    }
}


