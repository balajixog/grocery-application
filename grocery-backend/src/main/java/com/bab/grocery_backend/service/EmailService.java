package com.bab.grocery_backend.service;

public interface EmailService {

    void sendPasswordResetEmail(String to, String token);

}