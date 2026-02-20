package com.bab.grocery_backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bab.grocery_backend.entity.CartItem;
import com.bab.grocery_backend.entity.Cart;
import com.bab.grocery_backend.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}