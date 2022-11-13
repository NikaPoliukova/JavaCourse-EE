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
import javax.servlet.http.HttpSession;


@Log4j2
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
  }

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    String hashPass = userService.getUserPassword(username);
    if (hashPassService.verify(password, hashPass)) {
      User user = userService.getUser(username, hashPass);
      HttpSession session = req.getSession();
      session.setAttribute("username", username);
      session.setAttribute("userId", user.getUserId());
      resp.sendRedirect("users");
    } else {
      resp.sendRedirect("login?error=" + " enter incorrect password");
      log.error("An error occurred while logging into account ");
    }
  }
}
