package com.sliit.fyp.payload;

public class JwtAuthenticationResponse {

  private String accessToken;
  private String role;
  private Boolean success;

  public JwtAuthenticationResponse(String accessToken, String role, Boolean success) {
    this.accessToken = accessToken;
    this.role = role;
    this.success = success;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

}
