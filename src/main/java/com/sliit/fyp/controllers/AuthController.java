package com.sliit.fyp.controllers;

import com.sliit.fyp.payload.ApiResponse;
import com.sliit.fyp.payload.LoginRequest;
import com.sliit.fyp.payload.SignupRequest;
import com.sliit.fyp.services.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class AuthController {

  @Autowired
  AuthService authService;

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("/login")
  public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest loginRequest) {
    try {
      return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("Failed to authenticate the user");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse(false, "Failed to authenticate the user"));
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<Object> registerUser(@RequestBody SignupRequest signupRequest) {
    try {
      return ResponseEntity.ok(authService.registerUser(signupRequest));
    } catch (Exception e) {
      LOGGER.error("Failed to register the user");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse(false, "Failed to register the user"));
    }
  }

  @GetMapping("/test")
  public ResponseEntity<Object> test() {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "Testing completed"));
    } catch (Exception e) {
      LOGGER.error("testing failed");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse(false, "testing failed"));
    }
  }
}
