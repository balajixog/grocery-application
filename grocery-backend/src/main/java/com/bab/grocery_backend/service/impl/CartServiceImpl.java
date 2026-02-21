package com.bab.grocery_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bab.grocery_backend.dto.CartItemResponseDto;
import com.bab.grocery_backend.dto.CartResponseDto;
import com.bab.grocery_backend.dto.dtoRequest.AddToCartRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UpdateCartItemRequestDto;
import com.bab.grocery_backend.entity.Cart;
import com.bab.grocery_backend.entity.CartItem;
import com.bab.grocery_backend.entity.Product;
import com.bab.grocery_backend.entity.User;
import com.bab.grocery_backend.repository.CartItemRepository;
import com.bab.grocery_backend.repository.CartRepository;
import com.bab.grocery_backend.repository.ProductRepository;
import com.bab.grocery_backend.repository.UserRepository;
import com.bab.grocery_backend.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void addToCart(AddToCartRequestDto dto, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < dto.getQuantity()) {
            throw new RuntimeException("Not enough stock available");
        }

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder().user(user).build()
                ));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseGet(() -> CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(0)
                        .build()
                );

        cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        cartItemRepository.save(cartItem);
    }
        @Override
        public CartResponseDto viewCart(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        List<CartItemResponseDto> items = cartItemRepository.findAll()
                .stream()
                .filter(ci -> ci.getCart().getId().equals(cart.getId()))
                .map(ci -> new CartItemResponseDto(
                        ci.getProduct().getId(),
                        ci.getProduct().getName(),
                        ci.getProduct().getPrice(),
                        ci.getQuantity(),
                        ci.getProduct().getPrice() * ci.getQuantity()
                ))
                .toList();

        double total = items.stream()
                .mapToDouble(CartItemResponseDto::getTotalPrice)
                .sum();

        return new CartResponseDto(items, total);
}
        @Override
        public void updateCartItem(UpdateCartItemRequestDto dto, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Item not in cart"));

        if (product.getStockQuantity() < dto.getQuantity()) {
                throw new RuntimeException("Not enough stock available");
        }

        cartItem.setQuantity(dto.getQuantity());
        cartItemRepository.save(cartItem);
        }
        @Override
        public void removeCartItem(Long productId, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Item not in cart"));

        cartItemRepository.delete(cartItem);
        }
}