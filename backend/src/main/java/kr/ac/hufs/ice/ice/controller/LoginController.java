package kr.ac.hufs.ice.ice.controller;


import jakarta.servlet.http.HttpSession;
import kr.ac.hufs.ice.ice.dto.MemberDto;
import kr.ac.hufs.ice.ice.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody MemberDto loginRequestDto, HttpSession session) {
        boolean success = loginService.login(loginRequestDto.getStudentId(), loginRequestDto.getPassword());
        if (success) {
            session.setAttribute("studentId", loginRequestDto.getStudentId());
            return "로그인 성공";
        } else {
            return "로그인 실패: 학번 또는 비밀번호가 잘못되었습니다.";
        }
    }
}
