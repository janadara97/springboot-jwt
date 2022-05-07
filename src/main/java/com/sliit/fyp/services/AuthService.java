package com.sliit.fyp.services;

import com.sliit.fyp.payload.LoginRequest;
import com.sliit.fyp.payload.SignupRequest;

public interface AuthService {
  public Object authenticateUser(LoginRequest loginRequest);

  public Object registerUser(SignupRequest signupRequest);

}
