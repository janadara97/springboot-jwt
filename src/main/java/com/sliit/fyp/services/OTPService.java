package com.sliit.fyp.services;

import com.sliit.fyp.dto.OTPRequestDto;

import org.springframework.http.ResponseEntity;

public interface OTPService {

  public ResponseEntity<Object> sendOtp(OTPRequestDto otpRequest);

  public ResponseEntity<Object> validateOtp(OTPRequestDto otpValidation);

}
