package example.service;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;


public class HashPassServiceImpl implements HashPassService {
  BCrypt.Result verify;
  public String secret;

  public HashPassServiceImpl(String secret) {
    this.secret = secret;
  }

  public boolean verify(String password, String hashPass) {
    verify = BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), hashPass.getBytes());
    return verify.verified;
  }

  public String hashPass(String password) {
    final BCrypt.Hasher hasher = BCrypt.with(new SecureRandom(secret.getBytes(StandardCharsets.UTF_8)));
    return hasher.hashToString(12, password.toCharArray());
  }
}
