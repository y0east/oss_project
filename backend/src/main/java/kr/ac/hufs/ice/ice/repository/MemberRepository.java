package kr.ac.hufs.ice.ice.repository;

import kr.ac.hufs.ice.ice.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// TODO Refactoring 고려
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByStudentIdAndPassword(String studentId, String password);
}

