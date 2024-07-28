package com.priceguard.workflows.services;

import com.priceguard.core.service.GmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private GmailService gmailService;

    public void sendEmail(String email, Double price, String productName) {
        String subject = "Price-Guard: Product Price Dropped Down";
        String bodyText = String.format(
                "Dear User,\n\n" +
                        "We are writing to inform you that the price of %s has dropped down.\n" +
                        "The new price is Rs. %s .\n\n" +
                        "Thank you for using Price-Guard.\n" +
                        "- If you want to stop notifications for this Product, remove it from your Product List.\n", productName, price);
        try {
            gmailService.sendMessage(email, subject, bodyText);
        } catch (MessagingException | IOException | GeneralSecurityException e) {
            log.error("Failed to send OTP to: {}", email, e);
        }
    }
}
