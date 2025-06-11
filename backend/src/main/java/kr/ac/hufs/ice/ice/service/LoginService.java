package kr.ac.hufs.ice.ice.service;

import kr.ac.hufs.ice.ice.entity.member.Member;

public interface LoginService {
    public Member login(String studentId, String password);
}

