package com.sliit.fyp.dto;

public class OTPRequestDto {
  private String username;
  private String email;
  private String name;
  private int code;

  public OTPRequestDto(String username, String email, int code, String name) {
    this.username = username;
    this.email = email;
    this.code = code;
    this.name = name;
  }

  public OTPRequestDto() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
