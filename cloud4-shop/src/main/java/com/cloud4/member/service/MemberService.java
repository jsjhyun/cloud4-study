package com.cloud4.member.service;

import com.cloud4.member.entity.Member;

public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}