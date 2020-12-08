package com.phani.spring.accesscontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  private Map<String, String> emailMap = new HashMap<>();

  @PostConstruct
  public void init() {
      emailMap.put("amar", "amar@gmail.com");
      emailMap.put("akbar", "akbar@gmail.com");
      emailMap.put("anthony", "anthony@gmail.com");
  }

  public User findUserByUsername(String username) {
      UserDetails user = customUserDetailsService.loadUserByUsername(username);
      return User.builder()
              .email(emailMap.get(username))
              .username(username)
              .build();
  }

}
