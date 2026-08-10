package com.shopsphere.dto.order;

import com.shopsphere.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long orderId;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private List<OrderItemResponse> items;
}