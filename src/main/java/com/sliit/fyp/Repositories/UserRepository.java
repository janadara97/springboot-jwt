package com.sliit.fyp.Repositories;

import com.sliit.fyp.Models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  public User findByUserName(String username);

  Boolean existsByEmail(String email);

}
