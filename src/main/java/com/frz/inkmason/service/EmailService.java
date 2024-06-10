package com.frz.inkmason.service;

import com.frz.inkmason.dto.auth.EmailDetailsDto;
import com.frz.inkmason.model.person.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    public void sendEmail(EmailDetailsDto emailDetailsDto){
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailSender);
            simpleMailMessage.setTo(emailDetailsDto.getRecipient());
            simpleMailMessage.setText(emailDetailsDto.getMessageBody());
            simpleMailMessage.setSubject(emailDetailsDto.getSubject());

            javaMailSender.send(simpleMailMessage);
        }
        catch(MailException e){
            throw new RuntimeException(e);
        }

    }

    public EmailDetailsDto generateRegistrationOTPMail(User user, String otp) {
        String name = user.getFirstname();
        String link = "http://localhost:8085/inkmason/auth/verify?email="+ user.getEmail()+"&otp="+ otp;
        String message =  String.format("Dear %s,%n%nYou are receiving this email because you registered with InkhMason %n%n Use the One Time Password: %s%n%n or click the link %s to complete your registration%n%nThank you!", name, otp,link);
        return new EmailDetailsDto(
                user.getEmail(), message, "Verify your InkMason Account"
        );
    }

    public EmailDetailsDto generatePasswordResetOTPMail(User user, String otp) {
        String name = user.getFirstname();
        String message =  String.format("Dear %s,%n%nYou are receiving this email because you requested to change the password to your InkhMason Account. %n%n Use the One Time Password: %s%nThank you!", name, otp);
        return new EmailDetailsDto(
                user.getEmail(), message, "Change your InkhMason Account Password"
        );
    }

}
