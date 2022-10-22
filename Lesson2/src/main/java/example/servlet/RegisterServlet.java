package example.servlet;

import example.model.User;
import example.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
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

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final String userName = req.getParameter("username");
    final String password = req.getParameter("password");
    try {
      userService.addUser(new User(userName, password));
    } catch (SQLException e) {
      log.error("User in not added, error - " + e);
      e.printStackTrace();
    }
    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
  }
}
