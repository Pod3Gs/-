package com.scott.springweb.service.impl;

import com.scott.springweb.service.MessageService;
import com.scott.springweb.service.RedisService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired private RabbitTemplate rabbitTemplate;
  @Autowired private RedisService redisService;

  @RabbitListener(queues = "userActivity")
  public void getMQ(String message) {
    redisService.setMessage(message);
  }

  @Override
  public void sendRegister(String username) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    String context = username + "于" + date + "注册";
    this.rabbitTemplate.convertAndSend("userActivity", context);
  }

  @Override
  public void sendDelete(String username) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    String context = username + "于" + date + "注销";
    this.rabbitTemplate.convertAndSend("userActivity", context);
  }

  @Override
  public void sendChangePw(String username) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    String context = username + "于" + date + "更改密码";
    this.rabbitTemplate.convertAndSend("userActivity", context);
  }

  @Override
  public void sendLogin(String username) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    String context = username + "于" + date + "登陆";
    this.rabbitTemplate.convertAndSend("userActivity", context);
  }
}
