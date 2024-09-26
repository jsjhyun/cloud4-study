package com.cloud4.member.repository;

import com.cloud4.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository // 빈에 자동 스캔
public class MemberRepositoryImpl implements MemberRepository {
    /*
    static 사용 이유 : 동일한 저장소를 공유
    데이터베이스가 아직 확정이 안되고 개발은 진행해야 하는 상황
    메모리 회원 저장소를 구현해서 우선 개발을 진행
    참고: `HashMap` 은 동시성 이슈가 발생할 수 있다. 이런 경우 `ConcurrentHashMap` 을 사용
     */
    private static Map<Long, Member> store = new ConcurrentHashMap<>();

    /*
        저장 -> void 타입
        id : key. member : value
        member 모든 정보 저장
     */
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    /*
        memberId를 사용하여 member의 id임을 명시
     */
    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}