package com.scott.springweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringwebApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringwebApplication.class, args);
  }
}
