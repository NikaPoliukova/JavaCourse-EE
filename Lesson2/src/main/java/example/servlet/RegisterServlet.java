package example.servlet;

import at.favre.lib.crypto.bcrypt.BCrypt;
import example.model.User;
import example.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@WebServlet("/registration")
public class RegisterServlet extends HttpServlet {
  private UserService userService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    userService = (UserService) config.getServletContext().getAttribute("userService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
  }

  public static final String SECRET = "SECRET";

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final String userName = req.getParameter("username");
    final String password = req.getParameter("password");
    final BCrypt.Hasher hasher = BCrypt.with(new SecureRandom(SECRET.getBytes(StandardCharsets.UTF_8)));
    final String hashedPassword = hasher.hashToString(12, password.toCharArray());
    try {
      userService.addUser(new User(userName, hashedPassword));
    } catch (SQLException e) {
      log.error("User in not added, error - " + e);
      e.printStackTrace();
    }
    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
  }
}
