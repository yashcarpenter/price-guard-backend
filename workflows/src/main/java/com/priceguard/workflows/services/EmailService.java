package com.priceguard.workflows.services;

import org.springframework.stereotype.Service;

import com.nylas.NylasClient;
import com.nylas.models.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendEmail(String to, Double Price, String productName) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("yashcarpenter2@gmail.com");
//        message.setTo(to);
//        message.setSubject("Price-Guard: Product Price Dropped Down");
//        message.setText("Kya haal hai munna?");
//
//        javaMailSender.send(message);
//        System.out.println("Email sent successfully!");
//    }
//}
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, Double price, String productName) {
        // Construct email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yashcarpenter2@gmail.com");
        message.setTo(to);
        message.setSubject("Price-Guard: Product Price Dropped Down");

        // Customize email body with product details and price drop information
        String emailBody = "Dear User,\n\n";
        emailBody += "We are writing to inform you that the price of " + productName + " has dropped down.\n";
        emailBody += "The new price is Rs." + price + ".\n\n";
        emailBody += "Thank you for using Price-Guard.\n";

        message.setText(emailBody);

        // Send the email
        javaMailSender.send(message);
        System.out.println("Email sent successfully!");
    }
}
