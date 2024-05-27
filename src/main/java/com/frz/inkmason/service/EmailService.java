package com.frz.inkmason.service;

import com.frz.inkmason.dto.auth.EmailDetailsDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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


}
