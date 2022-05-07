package com.sliit.fyp.controllers;

import com.sliit.fyp.dto.OTPRequestDto;
import com.sliit.fyp.services.OTPService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/otp")
public class OTPController {

  @Autowired
  OTPService otpService;

  @PostMapping(value = "/sendOtpCode")
  public ResponseEntity<Object> sendOtpTest(@RequestBody OTPRequestDto otpRequest) {
    return ResponseEntity.status(HttpStatus.OK).body(otpService.sendOtp(otpRequest));
  }

  @PostMapping(value = "/validateOtpCode")
  public ResponseEntity<Object> validateOtpTest(
      @RequestBody OTPRequestDto otpValidation) {
    return ResponseEntity.status(HttpStatus.OK).body(otpService.validateOtp(otpValidation));
  }

}
