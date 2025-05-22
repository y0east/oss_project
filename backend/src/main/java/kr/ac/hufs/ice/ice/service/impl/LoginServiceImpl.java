package kr.ac.hufs.ice.ice.service.impl;

import kr.ac.hufs.ice.ice.repository.MemberRepository;
import kr.ac.hufs.ice.ice.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;

    @Override
    public boolean login(String studentId, String password) {
        return memberRepository.findByStudentIdAndPassword(studentId, password).isPresent();
    }
}

