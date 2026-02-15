package com.bab.grocery_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bab.grocery_backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
