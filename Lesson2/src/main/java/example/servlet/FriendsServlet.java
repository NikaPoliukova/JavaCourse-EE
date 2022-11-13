package example.servlet;

import example.model.User;
import example.service.FriendsService;
import lombok.SneakyThrows;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/friends")
public class FriendsServlet extends HttpServlet {

  private FriendsService friendsService;

  @SneakyThrows
  @Override
  public void init(ServletConfig config) {
    super.init(config);
    friendsService = (FriendsService) config.getServletContext().getAttribute("friendsService");
  }

  @SneakyThrows
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    long userId = (long) req.getSession().getAttribute("userId");
    List<User> friends = friendsService.findAllFriends(userId);
    req.setAttribute("friends", friends);
    getServletContext().getRequestDispatcher("/friends.jsp").forward(req, resp);
  }

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    final long sourceUserId = (long) req.getSession().getAttribute("userId");
    final long targetUserId = Long.parseLong(req.getParameter("targetUserId"));
    try {
      friendsService.cancelFriendship(sourceUserId, targetUserId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    resp.sendRedirect("friends");
  }
}
