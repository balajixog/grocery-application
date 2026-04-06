package com.bab.grocery_backend.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayClient;


@RestController
public class UserPayment {
    @Value("${razor.api-key}")
    private String key;

    @Value("${razor.api-secret}")
    private String secret;
    @PostMapping("/user/payment/create-order")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {

    try {
        System.out.println("Incoming Request: " + data);

        int amount = Integer.parseInt(data.get("amount").toString());
        System.out.println("Amount: " + amount);

        System.out.println("KEY: " + key);
        System.out.println("SECRET: " + secret);

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");

        com.razorpay.Order razorpayOrder = client.orders.create(orderRequest);

        System.out.println("Order Created: " + razorpayOrder);

        return ResponseEntity.ok(razorpayOrder.toString());

    } catch (Exception e) {
        e.printStackTrace(); 
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
}
