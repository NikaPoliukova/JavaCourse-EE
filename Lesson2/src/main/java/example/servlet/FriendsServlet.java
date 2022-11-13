package example.servlet;

import example.model.User;
import example.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/friends")
public class FriendsServlet extends HttpServlet {

  private UserService userService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    userService = (UserService) config.getServletContext().getAttribute("userService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<User> friends;
    long userId = (long) req.getSession().getAttribute("userId");
    friends = userService.findAllFriends(userId);
    req.setAttribute("friends", friends);
    getServletContext().getRequestDispatcher("/friends.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final long sourceUserId = (long) req.getSession().getAttribute("userId");
    final long targetUserId = Long.parseLong(req.getParameter("targetUserId"));
    try {
      userService.cancelFriendship(sourceUserId, targetUserId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    resp.sendRedirect("friends");
  }
}
