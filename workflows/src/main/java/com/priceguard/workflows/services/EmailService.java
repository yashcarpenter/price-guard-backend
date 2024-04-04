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

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yashcarpenter2@gmail.com");
        message.setTo("Biaorayashcarpenter@gmail.com");
        message.setSubject("Aur beta?");
        message.setText("Kya haal hai munna?");

        javaMailSender.send(message);
        System.out.println("Email sent successfully!");
    }
}

//@Service
//public class EmailService {
//    public void sendEmail() throws NylasSdkTimeoutError, NylasApiError {
//        try {
//            NylasClient nylas = new NylasClient.Builder("nyk_v0_HJEDO2ja1gG807sW3z3zDMzpwOl0zKO7ZeMAHWSX3kktkuZowtQKrp5UYs9iRbfw").build();
//
//            List<EmailName> emailNames = new ArrayList<>();
//            emailNames.add(new EmailName("biaorayashcarpenter@gmail.com", "Nylas"));
//
//            List<CreateAttachmentRequest> request = new ArrayList<>();
//
//            SendMessageRequest requestBody = new SendMessageRequest.Builder(emailNames).
//                    subject("With Love, from Nylas").
//                    body("This email was sent using the <b>Java SDK</b> for the Nylas Email API." +
//                            " Visit <a href='https://nylas.com'>Nylas.com</a> " +
//                            "for details.").
//                    attachments(request).
//                    build();
//
//            Response<Message> email = nylas.messages().send("3bd8f0ec-36cf-428b-a1fe-4d2d3ac80294", requestBody);
//
//            System.out.println(email.getData());
//        } catch (Exception e) {
//            System.err.println("An exception occurred: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}

