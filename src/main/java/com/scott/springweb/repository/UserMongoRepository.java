package com.scott.springweb.repository;

import com.scott.springweb.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, Long> {

  User findByUsername(String username);

  Page<User> findByPsId(int psId, Pageable pageable);

}
