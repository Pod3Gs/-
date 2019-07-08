package com.scott.springweb.web;

import com.scott.springweb.domain.User;
import com.scott.springweb.service.RedisService;
import com.scott.springweb.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
 *
 *由于本地打开涉及到跨域 所以用@CrossOrigin来解决
 *
 * */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:63342", "null"})
public class UserController {

  @Autowired private RedisService redisService;

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /*return      0：密码错误
                1：为管理员
                2：为普通用户
  */
  @GetMapping("/login")
  public Map<String, Object> login(String username, String password) {
    return Collections.singletonMap("matched", userService.login(username, password));
  }

  @GetMapping("/getMessage")
  public Map<String, Object> getMessage() {
    return Collections.singletonMap("message", redisService.getMessage());
  }

  @GetMapping("/getAllUsers")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/getAllUsersByPage")
  public Page<User> getAllUsersByPage(Pageable pageable) {
    return userService.getAllUsersByPage(pageable);
  }

  @GetMapping("/getUserListInfo")
  public Map<String, Long> getUserListInfo() {
    return Collections.singletonMap("totalElements", userService.getUserListInfo());
  }

  @GetMapping("/checkName")
  public Map<String, Object> checkName(String username) {
    return Collections.singletonMap("userExist", userService.checkName(username));
  }

  @GetMapping("/getUserInfo")
  public User getUserInfo(String username) {
    return userService.getUserInfo(username);
  }

  @DeleteMapping("/deleteUser")
  public Map<String, Object> deleteUser(String username, String password) {
    return Collections.singletonMap("delete_success", userService.deleteUser(username, password));
  }

  @PutMapping("/changePassword")
  public Map<String, Object> changePassword(String username, String password, String newPassword) {

    return Collections.singletonMap(
        "change_success", userService.changePassword(username, password, newPassword));
  }

  @PostMapping("/register")
  public Map<String, Object> register(String username, String password, int age, String gender) {
    return Collections.singletonMap(
        "reg_success", userService.register(username, password, age, gender, 2));
  }
}
