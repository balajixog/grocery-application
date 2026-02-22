package com.bab.grocery_backend.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import com.bab.grocery_backend.dto.OrderTrackingStepDto;
import com.bab.grocery_backend.dto.dtoRequest.AdminOrderResponseDto;
import com.bab.grocery_backend.dto.dtoRequest.OrderHistoryResponseDto;
import com.bab.grocery_backend.dto.dtoResponse.OrderDetailsResponseDto;
import com.bab.grocery_backend.dto.dtoResponse.OrderItemResponseDto;
import com.bab.grocery_backend.dto.dtoResponse.OrderTrackingResponseDto;
import com.bab.grocery_backend.entity.*;
import com.bab.grocery_backend.repository.*;
import com.bab.grocery_backend.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional //If any step fails -> everything rolls back
    public void placeOrder(String userEmail) {

        //  Get user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  Get cart
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        //  Get cart items
        List<CartItem> cartItems = cartItemRepository.findAll()
                .stream()
                .filter(ci -> ci.getCart().getId().equals(cart.getId()))
                .toList();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        //  Validate stock & calculate total
        double totalAmount = 0.0;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            totalAmount += product.getPrice() * cartItem.getQuantity();
        }

        //  Create Order
        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .status("PLACED")
                .createdAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        //  Convert CartItems → OrderItems + deduct stock
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            // deduct stock
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            // create order item
            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItemRepository.save(orderItem);
        }

        //  Clear cart
        cartItemRepository.deleteAll(cartItems);
    }
        // for history
        @Override
        public List<OrderHistoryResponseDto> getOrderHistory(String userEmail) {

                User user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                List<Order> orders = orderRepository.findByUser(user);

                return orders.stream()
                        .map(order -> new OrderHistoryResponseDto(
                                order.getId(),
                                order.getTotalAmount(),
                                order.getStatus(),
                                order.getCreatedAt()
                        ))
                        .toList();
        }

        @Override
        public Page<AdminOrderResponseDto> getAllOrders(int page, int size, String sortBy, String direction) {

                Sort sort = direction.equalsIgnoreCase("desc")
                        ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending();

                Pageable pageable = PageRequest.of(page, size, sort);

                return orderRepository.findAll(pageable)
                        .map(order -> new AdminOrderResponseDto(
                                order.getId(),
                                order.getUser().getEmail(),
                                order.getTotalAmount(),
                                order.getStatus(),
                                order.getCreatedAt()
                        ));
        }

        @Override
        public void updateOrderStatus(Long orderId, String status) {

                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found"));

                // Basic validation of allowed transitions 
                if (!status.matches("PLACED|SHIPPED|DELIVERED|CANCELLED")) {
                throw new RuntimeException("Invalid status");
                }

                order.setStatus(status);
                orderRepository.save(order);
        }

        @Override
                @Transactional
                public void cancelOrder(Long orderId) {

                //  Fetch order
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found"));

                //  Validate status
                if (order.getStatus().equals("DELIVERED")) {
                        throw new RuntimeException("Delivered order cannot be cancelled");
                }

                if (order.getStatus().equals("CANCELLED")) {
                        throw new RuntimeException("Order already cancelled");
                }

                //  Get all order items
                List<OrderItem> orderItems = orderItemRepository.findByOrder(order);

                //  Restore stock
                for (OrderItem item : orderItems) {
                        Product product = item.getProduct();
                        product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                        productRepository.save(product);
                }

                //  Update order status
                order.setStatus("CANCELLED");
                orderRepository.save(order);
        }

        @Override
        public OrderDetailsResponseDto getOrderDetails(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);

        List<OrderItemResponseDto> items = orderItems.stream()
                .map(item -> new OrderItemResponseDto(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderDetailsResponseDto(
                order.getId(),
                order.getUser().getEmail(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                items
        );
        }
        @Override
        public OrderTrackingResponseDto trackOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String status = order.getStatus();

        List<OrderTrackingStepDto> timeline = List.of(
                new OrderTrackingStepDto("PLACED", true),
                new OrderTrackingStepDto("SHIPPED", status.equals("SHIPPED") || status.equals("DELIVERED")),
                new OrderTrackingStepDto("DELIVERED", status.equals("DELIVERED"))
        );

        if (status.equals("CANCELLED")) {
                timeline = List.of(
                        new OrderTrackingStepDto("PLACED", true),
                        new OrderTrackingStepDto("CANCELLED", true)
                );
        }

        return new OrderTrackingResponseDto(orderId, status, timeline);
        }
}