package example.servlet;

import example.model.User;
import example.service.HashPassService;
import example.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Log4j2
@WebServlet("/registration")
public class RegisterServlet extends HttpServlet {
  private UserService userService;
  private HashPassService hashPassService;

  @SneakyThrows
  @Override
  public void init(ServletConfig config) {
    super.init(config);
    userService = (UserService) config.getServletContext().getAttribute("userService");
    hashPassService = (HashPassService) config.getServletContext().getAttribute("hashPassService");
  }

  @SneakyThrows
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
  }


  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    final String userName = req.getParameter("username");
    final String password = req.getParameter("password");
    try {
      userService.addUser(new User(userName, hashPassService.hashPass(password)));
    } catch (SQLException e) {
      log.error("User in not added, error - " + e);
      e.printStackTrace();
    }
    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
  }
}
