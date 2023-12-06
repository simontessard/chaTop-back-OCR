package com.chatop.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.chatop.api.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByUsername(String username);
}
