package com.scott.springweb.service;

public interface MessageService {

  void sendRegister(String username);

  void sendDelete(String username);

  void sendChangePw(String username);

  void sendLogin(String username);
}
