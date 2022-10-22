package example.servlet;

import example.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private UserService userService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    userService = (UserService) config.getServletContext().getAttribute("userService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    try {
      if (userService.checkRegistered(username, password)) {
        HttpSession session = req.getSession(true);
        session.setAttribute("username", username);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    resp.sendRedirect("users"); //в данном слуучае мы всегда отправляем на страницу пользователей,
    // если пользователь не валидный - его лучше перенаправить
    // на отдельную страницу с ошибкой, либо как-то передать сообщение об ошибке на логин страницу)
  }
}
