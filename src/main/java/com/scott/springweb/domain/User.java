package com.scott.springweb.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {

  @Id private String id;

  private String username;
  private String password;
  private int age;
  private String gender;

  private int psId;

  public User() {}

  public User(String username, String password, int age, String gender, int psId) {
    this.username = username;
    this.password = password;
    this.age = age;
    this.gender = gender;
    this.psId = psId;
  }
}
