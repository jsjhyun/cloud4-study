package com.cloud4.config;

import com.cloud4.member.repository.MemberRepository;
import com.cloud4.member.repository.MemberRepositoryImpl;
import com.cloud4.member.service.MemberService;
import com.cloud4.member.service.MemberServiceImpl;
import com.cloud4.order.discount.DiscountPolicy;
import com.cloud4.order.discount.RateDiscountPolicy;
import com.cloud4.order.service.OrderService;
import com.cloud4.order.service.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                discountPolicy(),
                memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}