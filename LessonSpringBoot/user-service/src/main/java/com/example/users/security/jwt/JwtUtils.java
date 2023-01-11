package com.example.users.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtils {
  public static String generateAccessToken(User user, String requestUrl, Algorithm algorithm) {
    return JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000))
        .withIssuer(requestUrl)
        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .sign(algorithm);
  }

  public static String generateRefreshToken(User user, String requestUrl, Algorithm algorithm) {
    return JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
        .withIssuer(requestUrl)
        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .sign(algorithm);
  }

  public static String generateRefreshedAccessToken(String username, String requestUrl, String secretKey) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
    List<String> roles = new ArrayList<>();
    roles.add("USER");
    return JWT.create()
        .withSubject(username)
        .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
        .withIssuer(requestUrl)
        .withClaim("roles", roles)
        .sign(algorithm);
  }

  public static String getUsernameFromRefreshToken(String refreshToken, String secretKey) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedJWT = verifier.verify(refreshToken);
    return decodedJWT.getSubject();
  }

  public static DecodedJWT getDecodedJWTToken(String token, String secretKey) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    return verifier.verify(token);
  }
}
