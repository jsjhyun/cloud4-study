package com.cloud4.order.service;

import com.cloud4.order.entity.Order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}