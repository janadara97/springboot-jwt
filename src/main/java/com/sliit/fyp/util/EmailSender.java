package com.sliit.fyp.util;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

import com.sliit.fyp.dto.OTPRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

  @Autowired
  JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String mailSender;

  private String endMessage = "Thanks, \nBest Regards";

  public boolean sendEmail(String email, String subject, String text) {
    try {
      MimeMessagePreparator mail = mimeMessage -> {
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        mimeMessage.setFrom(new InternetAddress(mailSender, "FYP"));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(text);

      };
      InternetAddress internetAddress = new InternetAddress(email);
      internetAddress.validate();
      javaMailSender.send(mail);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public Boolean sendLoginOtp(OTPRequestDto otpRequest) {

    String subject = "Login  request for user with account: " + otpRequest.getName();
    String text = "Hi \n\n"
        + "Please use the following OTP to Login in to the System. If you didn't make this request, ignore this email. \n\nOTP - "
        + otpRequest.getCode() + "\n\n" + endMessage;

    return sendEmail(otpRequest.getEmail(), subject, text);
  }

  public Boolean sendLoginUsername(OTPRequestDto emailRequestDto) {

    String subject = "Username for user with account: " + emailRequestDto.getUsername();
    String text = "Hi " + emailRequestDto.getName() + ",\n\n"
        + "Please use the following username for Login in to the System. If you didn't make this request, ignore this email. \n\nUsername - "
        + emailRequestDto.getUsername() + "\n\n"
        + endMessage;

    return sendEmail(emailRequestDto.getEmail(), subject, text);
  }

}
