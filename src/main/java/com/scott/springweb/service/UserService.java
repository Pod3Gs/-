package com.scott.springweb.service;

import com.scott.springweb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

  int login(String username, String password);

  boolean register(String username, String password, int age, String gender, int psId);

  boolean changePassword(String username, String password, String newPassword);

  boolean checkName(String username);

  boolean deleteUser(String username, String password);

  User getUserInfo(String username);

  List<User> getAllUsers();

  Page<User> getAllUsersByPage(Pageable pageable);

  long getUserListInfo();
}
