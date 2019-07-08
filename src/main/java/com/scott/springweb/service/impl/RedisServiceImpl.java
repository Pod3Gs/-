package com.scott.springweb.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scott.springweb.domain.User;
import com.scott.springweb.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
  @Autowired JedisPool jedisPool;

  @Override
  public User getUser(String username) {
    Jedis jedis = jedisPool.getResource();
    String user = jedis.get(username);
    User getUser = null;
    ObjectMapper mapper = new ObjectMapper();
    try {
      getUser = mapper.readValue(user, User.class);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      jedis.close();
    }
    return getUser;
  }



  @Override
  public boolean exist(String username) {
    Jedis jedis = jedisPool.getResource();
    boolean value = jedis.exists(username);
    jedis.close();
    return value;
  }

  @Override
  public void deleteUser(String username) {
    Jedis jedis = jedisPool.getResource();
    jedis.del(username);
    jedis.close();
  }

  @Override
  public void setUser(User user) {
    Jedis jedis = jedisPool.getResource();
    ObjectMapper mapper = new ObjectMapper();
    try {
      String value = mapper.writeValueAsString(user);
      jedis.set(user.getUsername(), value);
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      jedis.close();
    }
  }

  @Override
  public void setMessage(String message) {
    Jedis jedis = jedisPool.getResource();
    jedisPool.getResource();
    jedis.lpush("message", message);
    jedis.close();
  }

  @Override
  public String getMessage() {
    Jedis jedis = jedisPool.getResource();
    String value = jedis.lpop("message");
    jedis.close();
    return value;
  }

  @Override
  public String get(String key) {
    Jedis jedis = jedisPool.getResource();
    String value = jedis.get(key);
    jedis.close();
    return value;
  }
}
