package com.scott.springweb.service.impl;

import com.scott.springweb.domain.User;
import com.scott.springweb.repository.UserMongoRepository;
import com.scott.springweb.service.MessageService;
import com.scott.springweb.service.RedisService;
import com.scott.springweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired UserMongoRepository userMongoRepository;

  @Autowired RedisService redisService;

  @Autowired MessageService messageService;

  @Autowired MongoTemplate mongoTemplate;

  private String encoderByMd5(String str) {
    // 确定计算方法

    String newstr = null;
    MessageDigest md5;
    try {
      md5 = MessageDigest.getInstance("MD5");
      BASE64Encoder base64en = new BASE64Encoder();
      // 加密后的字符串
      newstr = base64en.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    return newstr;
  }

  private User getUserByLogin(String username, String password) {
    User user;
    if (redisService.exist(username)) {
      log.info("login from redis");
      user = redisService.getUser(username);
    } else {
      log.info("login from mysql");
      user = userMongoRepository.findByUsername(username);
      redisService.setUser(user);
    }
    log.info(String.valueOf(user));
    if (user == null) {
      return null;
    } else if (encoderByMd5(password).equals(user.getPassword())) {
      return user;
    } else {
      return null;
    }
  }

  @Override
  public int login(String username, String password) {
    User user = getUserByLogin(username, password);
    if (user != null) {
      messageService.sendLogin(username);
      return user.getPsId();
    } else {
      return 0;
    }
  }

  @Override
  public boolean deleteUser(String username, String password) {
    User user = getUserByLogin(username, password);
    if (user != null) {
      messageService.sendDelete(username);
      if (redisService.exist(username)) {
        redisService.deleteUser(username);
      }
      userMongoRepository.delete(user);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public List<User> getAllUsers() {
    return userMongoRepository.findAll();
  }

  @Override
  public User getUserInfo(String username) {
    if (redisService.exist(username)) {
      return redisService.getUser(username);
    } else {
      return userMongoRepository.findByUsername(username);
    }
  }

  /*@@@@@@@@@@@@@@@@@@    new    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
  @Override
  public long getUserListInfo() {
    return mongoTemplate.count(new Query(Criteria.where("psId").is(2)), "user");
  }

  @Override
  public Page<User> getAllUsersByPage(Pageable pageable) {
    return userMongoRepository.findByPsId(2, pageable);
  }

  @Override
  public boolean register(String username, String password, int age, String gender, int psId) {
    if (!checkName(username)) {
      User newUser = new User(username, encoderByMd5(password), age, gender, psId);
      userMongoRepository.save(newUser);
      redisService.setUser(getUserInfo(username));
      messageService.sendRegister(username);
      return true;
    } else {
      return false;
    }
  }

  private void updatePassword(String username, String newPassword) {
    User user = userMongoRepository.findByUsername(username);
    user.setPassword(encoderByMd5(newPassword));
    userMongoRepository.save(user);
  }

  @Override
  public boolean changePassword(String username, String password, String newPassword) {
    User user = getUserByLogin(username, password);
    if (user != null && user.getPsId() == 2) {
      updatePassword(username, newPassword);
      redisService.deleteUser(username);
      messageService.sendChangePw(username);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean checkName(String username) {
    User user = userMongoRepository.findByUsername(username);
    return (user != null);
  }
}
