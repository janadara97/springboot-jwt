package com.sliit.fyp.payload;

public class SignupRequest {

  private String name;
  private String email;
  private String password;
  private int number;
  private String role;

  public SignupRequest(String name, String email, int number, String role) {
    this.name = name;
    this.email = email;
    this.number = number;
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
