package com.example.users.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class HashPassServiceImpl {
  private final BCrypt.Hasher hasher;

  public HashPassServiceImpl() {
    hasher = BCrypt.with(new SecureRandom("secret".getBytes()));
  }

  public boolean verify(String password, String hashPass) {
    final BCrypt.Result verify = BCrypt.verifyer().verify(password.getBytes(), hashPass.getBytes());
    return verify.verified;
  }

  public String hashPass(String password) {
    return hasher.hashToString(12, password.toCharArray());
  }
}

