package kr.ac.hufs.ice.ice.service.impl;

import kr.ac.hufs.ice.ice.entity.member.Member;
import kr.ac.hufs.ice.ice.repository.MemberRepository;
import kr.ac.hufs.ice.ice.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final MemberRepository memberRepository;

    @Override
    public boolean register(String studentId, String password) {
        boolean exists = memberRepository.findByStudentIdAndPassword(studentId, password).isPresent();
        if (exists) {
            return false;
        }

        // TODO Refactoring
        Member newMember = new Member();
        newMember.setStudentId(studentId);
        newMember.setPassword(password);
        memberRepository.save(newMember);
        return true;
    }
}

