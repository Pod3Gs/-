package com.scott.springweb;

import com.scott.springweb.service.RedisService;
import com.scott.springweb.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringwebApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired private UserService userService;

    @Autowired private RedisService redisService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void setMessage(){
        String context =  "测试";
        rabbitTemplate.convertAndSend("userActivity", context);
    }

    @Test
    //@RabbitListener(queues = "userActivity")
    public void getMQTest(){
        String s = redisService.get("111");
    System.out.println(s);
    }

}
