package com.shopsphere.controller;

import com.shopsphere.dto.order.OrderRequest;
import com.shopsphere.dto.order.OrderResponse;
import com.shopsphere.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody OrderRequest request) {

        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}
