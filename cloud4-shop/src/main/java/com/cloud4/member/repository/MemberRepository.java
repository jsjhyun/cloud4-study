package com.cloud4.member.repository;

import com.cloud4.member.entity.Member;

public interface MemberRepository {
    void save(Member member);

    Member findById(Long id);
}
