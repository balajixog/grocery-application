package com.bab.grocery_backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bab.grocery_backend.entity.Address;
import com.bab.grocery_backend.entity.User;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}