package com.sliit.fyp.security;

import com.sliit.fyp.Models.User;
import com.sliit.fyp.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserPrinciple loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUserName(username);
    return UserPrinciple.create(user);
  }

}
