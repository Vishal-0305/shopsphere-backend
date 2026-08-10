package com.shopsphere.service;

import com.shopsphere.dto.order.OrderRequest;
import com.shopsphere.dto.order.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getOrdersByUser(Long userId);

}