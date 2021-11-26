package com.induk.python.pythoninweb.repository;

import com.induk.python.pythoninweb.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository {

    String memberJoinCheck(Member member);
    void memberJoinInsert(Member member);
    String memberTrueCheck(Member member);
    String memberNameCheck(String id);
    Member memberSelect(String login_id);

}
