package com.cloud4.member.service;

import com.cloud4.config.AppConfig;
import com.cloud4.member.Grade;
import com.cloud4.member.entity.Member;
import com.cloud4.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    @DisplayName("회원가입")
    void join() throws Exception {
        //given
        Member member = new Member(1L, "홍길동 ", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        /*
            then
            assertj import -> assertThat 시용
            회원가입 -> 찾은 회원 정보 같은 지 확인
         */
        Assertions.assertThat(member).isEqualTo(findMember);
    }

    @Test
    void findMember() {
    }
}