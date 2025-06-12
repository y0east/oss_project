package kr.ac.hufs.ice.ice.controller;


import jakarta.servlet.http.HttpServletResponse;
import kr.ac.hufs.ice.ice.dto.MemberDto;
import kr.ac.hufs.ice.ice.entity.member.Member;
import kr.ac.hufs.ice.ice.service.LoginService;
import kr.ac.hufs.ice.ice.service.TokenService;
import kr.ac.hufs.ice.ice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberDto loginRequestDto, HttpServletResponse response) {
        Member member = loginService.login(loginRequestDto.getStudentId(), loginRequestDto.getPassword());

        if (member != null) {
            String accessToken = jwtUtil.generateAccessToken(member.getStudentId(), member.getRole());
            String refreshToken = jwtUtil.generateRefreshToken(member.getStudentId());

            tokenService.saveRefreshToken(member.getStudentId(), refreshToken, JwtUtil.REFRESH_TOKEN_EXP);

            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Lax")
                    .build();
            response.setHeader("Set-Cookie", cookie.toString());

            Map<String, String> body = new HashMap<>();
            body.put("message", "로그인 성공");
            body.put("accessToken", accessToken);
            body.put("refreshToken", refreshToken);
            return ResponseEntity.ok(body);
        } else {
            Map<String, String> body = new HashMap<>();
            body.put("message", "로그인 실패: 학번 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
    }
}