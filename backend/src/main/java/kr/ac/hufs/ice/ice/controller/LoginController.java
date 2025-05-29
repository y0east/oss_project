package kr.ac.hufs.ice.ice.controller;


import jakarta.servlet.http.HttpSession;
import kr.ac.hufs.ice.ice.dto.MemberDto;
import kr.ac.hufs.ice.ice.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//Todo 무조건 refactoring
// 일단 토큰 기반으로 바꾸기 (지금은 이름만 token, 기능은 sessionid임)


@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody MemberDto loginRequestDto, HttpSession session) {
        boolean success = loginService.login(loginRequestDto.getStudentId(), loginRequestDto.getPassword());

        if (success) {
            session.setAttribute("studentId", loginRequestDto.getStudentId());

            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 성공");
            response.put("sessionToken", session.getId());  // 세션 ID 포함

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "로그인 실패: 학번 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
