package com.cloud4.order.service;

import com.cloud4.annotation.MainDiscountPolicy;
import com.cloud4.order.entity.Order;
import com.cloud4.member.entity.Member;
import com.cloud4.member.repository.MemberRepository;
import com.cloud4.order.discount.DiscountPolicy;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // @Autowired 생략 가능
    public OrderServiceImpl(@MainDiscountPolicy DiscountPolicy discountPolicy, MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); // 회원 정보 조회
        int discountPrice = discountPolicy.discount(member, itemPrice); // 할인 정책 적용
        return new Order(memberId, itemName, itemPrice, discountPrice); // 다음 주문 객체 생성 반환
    }
}