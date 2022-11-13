package example.servlet;


import example.model.User;
import example.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;


@Log4j2
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private UserService userService;

  @SneakyThrows
  @Override
  public void init(ServletConfig config) {
    super.init(config);
    userService = (UserService) config.getServletContext().getAttribute("userService");
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
    final Result verify = BCrypt.verifyer()
        .verify(password.getBytes(StandardCharsets.UTF_8), hashPass.getBytes());
    System.out.printf("");
    if (verify.verified) {
      //if (userService.checkRegistered(username, password)) {
      User user = userService.getUser(username, hashPass);
      HttpSession session = req.getSession(true);
      session.setAttribute("username", username);
      session.setAttribute("userId", user.getUserId());
      resp.sendRedirect("users");
    } else {
      resp.sendRedirect("users?error=" + "incorrect password");
      log.error("An error occurred while logging into account ");
    }
  }
}
