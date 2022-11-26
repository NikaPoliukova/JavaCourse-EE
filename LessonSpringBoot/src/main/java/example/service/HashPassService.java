package example.service;

public interface HashPassService {
  boolean verify(String password, String hashPass);

  String hashPass(String password);
}
