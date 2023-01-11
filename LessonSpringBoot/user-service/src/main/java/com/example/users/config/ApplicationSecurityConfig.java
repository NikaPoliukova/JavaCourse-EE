package com.example.users.config;

import com.example.users.security.filter.CustomAuthenticationFilter;
import com.example.users.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
  @Value("${jwt.secretKey}") private String secretKey;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), secretKey);
    customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests().antMatchers("/api/v1/token/refresh").permitAll();
    http.authorizeRequests().antMatchers("/api/v1/login").permitAll();


    http.authorizeRequests().antMatchers(GET, "/api/**").hasAuthority("USER");
    http.authorizeRequests().antMatchers("/**").permitAll();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
  }
}
