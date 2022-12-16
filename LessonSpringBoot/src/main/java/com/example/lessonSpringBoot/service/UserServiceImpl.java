package com.example.lessonSpringBoot.service;


import com.example.lessonSpringBoot.model.User;
import com.example.lessonSpringBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
 
  private final UserRepository userRepository;
  private final HashPassServiceImpl hashPassService;
  private final PasswordEncoder passwordEncoder;

  public Page<User> getFilteredUsers(String searchValue, int pageNumber, int pageSize) {
    Pageable page = PageRequest.of(pageNumber, pageSize);
    if (searchValue != null) {
      return userRepository.findUsersByUserNameStartingWith(searchValue, page);
    } else {
      return userRepository.findAllBy(page);
    }
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Transactional
  public void addUser(String userName, String password) {
    if (userRepository.findUserByUserName(userName) != null) {
      throw new RuntimeException("This user already exists");
    }
    //String hashPass = hashPassService.hashPass(password);
    String hashPass = passwordEncoder.encode(password);
    userRepository.addUser(userName, hashPass);
  }

  public User findUserByUserNameAndPassword(String name, String password) {
    User user = userRepository.findUserByUserName(name);
    if (user !=null && hashPassService.verify(password, user.getPassword())) {
      return user;
    } else {
      throw new RuntimeException("enter incorrect password");
    }
  }

  public User findUserByUserName(String userName) {
    return userRepository.findUserByUserName(userName);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findUserByUserName(username);
    if (user == null) {
      throw new RuntimeException("username not found in database");
    } else {
      log.info("user found in database");
    }
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("USER"));//TODO user's ROLE
    return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
  }
}

