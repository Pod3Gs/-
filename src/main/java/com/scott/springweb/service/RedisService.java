package com.scott.springweb.service;

import com.scott.springweb.domain.User;

public interface RedisService {
  User getUser(String username);

  boolean exist(String username);

  void deleteUser(String username);

  void setUser(User user);

  void setMessage(String message);

  String getMessage();

  String get(String key);
}
