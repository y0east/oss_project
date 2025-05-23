package kr.ac.hufs.ice.ice.controller;


import kr.ac.hufs.ice.ice.dto.MemberDto;
import kr.ac.hufs.ice.ice.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    // TODO Refactoring
    private final RegisterService registerService;
    @PostMapping()
    public String register(@RequestBody MemberDto memberDto) {
        boolean result = registerService.register(memberDto.getStudentId(), memberDto.getPassword());
        if (result) {
            return "회원가입 성공";
        } else {
            return "회원가입 실패: 이미 존재하는 학번입니다.";
        }
    }
}

