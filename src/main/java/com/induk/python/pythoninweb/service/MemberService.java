package com.induk.python.pythoninweb.service;

import com.induk.python.pythoninweb.domain.Member;
import com.induk.python.pythoninweb.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String memberJoinCheck(Member member) {
        return memberRepository.memberJoinCheck(member);
    }

    public String memberNameCheck(String id) {
        return memberRepository.memberNameCheck(id);
    }

    public String memberTrueCheck(Member member) {
        return memberRepository.memberTrueCheck(member);
    }

    public void memberJoinInsert(Member member) {
        memberRepository.memberJoinInsert(member);
    }

    public Member memberSelect(String login_id) { return memberRepository.memberSelect(login_id); }

}
