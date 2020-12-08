package com.phani.spring.accesscontrol;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private Set<User> users;

    @PostConstruct
    public void loadUsers() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        users = Stream.of(
                  new User("amar", passwordEncoder.encode("password"), Arrays.asList((GrantedAuthority)() -> "READ", (GrantedAuthority)()->"WRITE")),
                  new User("akbar", passwordEncoder.encode("password"), Arrays.asList((GrantedAuthority)() -> "READ", (GrantedAuthority)()->"WRITE")),
                  new User("anthony", passwordEncoder.encode("password"), Arrays.asList((GrantedAuthority)() -> "READ_ADMIN", (GrantedAuthority)()->"WRITE_ADMIN", (GrantedAuthority)()->"READ", (GrantedAuthority)()->"WRITE")
                )).collect(Collectors.toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow( () -> new UsernameNotFoundException("User not found: " + username));
    }
}
