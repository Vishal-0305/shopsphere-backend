package com.shopsphere.service.impl;

import com.shopsphere.dto.order.OrderItemRequest;
import com.shopsphere.dto.order.OrderItemResponse;
import com.shopsphere.dto.order.OrderRequest;
import com.shopsphere.dto.order.OrderResponse;
import com.shopsphere.entity.Order;
import com.shopsphere.entity.OrderItem;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.User;
import com.shopsphere.enums.OrderStatus;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.repository.OrderRepository;
import com.shopsphere.repository.ProductRepository;
import com.shopsphere.repository.UserRepository;
import com.shopsphere.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + request.getUserId()));

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PLACED)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Product not found with id: "
                                            + itemRequest.getProductId()));

            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product : "
                                + product.getName());
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItems.add(orderItem);

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);

            product.setQuantity(
                    product.getQuantity() - itemRequest.getQuantity());

            productRepository.save(product);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + orderId));

        return mapToOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemResponse> itemResponses = order.getOrderItems()
                .stream()
                .map(this::mapToOrderItemResponse)
                .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .items(itemResponses)
                .build();
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {

        BigDecimal totalPrice = orderItem.getPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        return OrderItemResponse.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .totalPrice(totalPrice)
                .build();
    }
}