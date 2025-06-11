package kr.ac.hufs.ice.ice.controller;


import jakarta.servlet.http.HttpServletResponse;
import kr.ac.hufs.ice.ice.dto.MemberDto;
import kr.ac.hufs.ice.ice.service.LoginService;
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
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberDto loginRequestDto, HttpServletResponse response) {
        boolean success = loginService.login(loginRequestDto.getStudentId(), loginRequestDto.getPassword());

        if (success) {
            String jwtToken = jwtUtil.generateToken(loginRequestDto.getStudentId());

            ResponseCookie cookie = ResponseCookie.from("token", jwtToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Lax")
                    .build();
            response.setHeader("Set-Cookie", cookie.toString());

            Map<String, String> body = new HashMap<>();
            body.put("message", "로그인 성공");
            return ResponseEntity.ok(body);
        } else {
            Map<String, String> body = new HashMap<>();
            body.put("message", "로그인 실패: 학번 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
    }

}