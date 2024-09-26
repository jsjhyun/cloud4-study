package com.cloud4.config;

import com.cloud4.config.AppConfig;
import com.cloud4.member.service.MemberService;
import com.cloud4.member.service.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class AppConfigTest {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 조회 테스트")
    void findBeanByName() throws Exception {
        //given. 컨테이너 이름 : memberService
        MemberService memberService = context.getBean("memberService", MemberService.class);

        //when

        //then
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
}