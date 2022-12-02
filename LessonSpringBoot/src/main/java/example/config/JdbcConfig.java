package example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;


@Configuration
public class JdbcConfig {

  @Bean
  public Connection connection(JdbcTemplate template) throws SQLException {
    return template.getDataSource().getConnection();
  }
}
