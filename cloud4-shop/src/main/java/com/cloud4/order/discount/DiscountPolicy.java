package com.cloud4.order.discount;

import com.cloud4.member.entity.Member;

public interface DiscountPolicy {

    int discount(Member member, int price); // 고객, 가격
}
