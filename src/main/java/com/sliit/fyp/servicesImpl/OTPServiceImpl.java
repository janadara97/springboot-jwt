package com.sliit.fyp.servicesImpl;

import com.sliit.fyp.dto.OTPRequestDto;
import com.sliit.fyp.payload.ApiResponse;
import com.sliit.fyp.services.OTPService;
import com.sliit.fyp.util.EmailSender;
import com.sliit.fyp.util.OTP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OTPServiceImpl implements OTPService {

  @Autowired
  OTP otpService;

  @Autowired
  EmailSender emailSender;

  private static final Logger LOGGER = LoggerFactory.getLogger(OTPServiceImpl.class);

  @Override
  public ResponseEntity<Object> sendOtp(OTPRequestDto otpRequest) {

    String sendOtpError = "Could not send verification email";
    try {
      String email = otpRequest.getEmail().trim().toLowerCase();

      Integer otp = otpService.generateOtp(email);
      OTPRequestDto otpRequestDto = new OTPRequestDto();
      otpRequestDto.setCode(otp);
      otpRequestDto.setEmail(email);

      // Send otp code via email

      if (Boolean.FALSE.equals(emailSender.sendLoginOtp(otpRequestDto))) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse(false, sendOtpError));
      }

      return ResponseEntity.ok()
          .body(new ApiResponse(true, "Login OTP code sent"));

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, e.getMessage()));

    }

  }

  @Override
  public ResponseEntity<Object> validateOtp(OTPRequestDto otpValidation) {

    HttpStatus status = HttpStatus.OK;
    boolean success = false;
    String message = "OTP validated";
    String email = otpValidation.getEmail().trim().toLowerCase();

    try {

      Integer confirmCode = otpService.getOtp(email);

      if (confirmCode == 0 || confirmCode == null) {
        LOGGER.warn("No confirm code set for user with email :{} ", email);
        status = HttpStatus.BAD_REQUEST;
        message = "No confirm code set for user";
      } else {
        if (confirmCode.equals(otpValidation.getCode())) {
          LOGGER.info("OTP Validated Successflly with email: {}", email);
          status = HttpStatus.OK;
          message = "OTP Validated";
          success = true;
          otpService.clearOTP(email);

        } else {
          LOGGER.info("Invalid OTP used for user with email: {}", email);
          status = HttpStatus.UNAUTHORIZED;
          message = "Invalid OTP";

        }
      }

    } catch (Exception e) {
      LOGGER.error("Unable to verify OTP for user with email: {}", email);
      e.printStackTrace();
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      message = "Unable to verify OTP";
    }
    return ResponseEntity.status(status).body((new ApiResponse(success, message)));
  }

}
