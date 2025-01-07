package com.example.shoeshub.module.buyer.service;

public interface SendMailService {

    void sendSimpleMail(String toEmail,
                        String subject,
                        String body);
}
