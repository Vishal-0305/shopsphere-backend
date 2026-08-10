package com.shopsphere.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequest> items;
}