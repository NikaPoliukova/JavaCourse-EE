package example.listener;


import example.repository.UserRepository;
import example.repository.UserRepositoryImpl;
import example.service.UserService;
import example.service.UserServiceImpl;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
@WebListener
public class DependencyInitializationContextListener implements ServletContextListener {
  @Override
  public void contextInitialized(final ServletContextEvent sce) {
    final String dbDriver = "org.postgresql.Driver";
    final String username = sce.getServletContext().getInitParameter("db_user");
    final String password = sce.getServletContext().getInitParameter("db_password");
    final String dbUrl = sce.getServletContext().getInitParameter("db_url");

    try {
      Class.forName(dbDriver);
      final Connection con = DriverManager.getConnection(dbUrl, username, password);
      UserRepository repository = new UserRepositoryImpl(con);
      UserService userService = new UserServiceImpl(repository);
      sce.getServletContext().setAttribute("userService", userService);
    } catch (Exception e) {
      log.fatal("не получилось создать соединение с базой данных по URL" + dbUrl);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    try {
      final Connection connection = (Connection) sce.getServletContext().getAttribute("connection");
      connection.close();
    } catch (SQLException e) {
      log.error("servlet close error " + e);
      e.printStackTrace();
    }
  }
}
