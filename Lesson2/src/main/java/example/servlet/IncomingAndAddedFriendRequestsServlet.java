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

@WebServlet("/incoming_and_added_friend_requests")
public class IncomingAndAddedFriendRequestsServlet extends HttpServlet {

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
    List<User> incomingFriendRequests = friendsService.findIncomingFriendRequests(userId);
    req.setAttribute("incomingFriendRequests", incomingFriendRequests);
    getServletContext().getRequestDispatcher("/incoming_friend_requests.jsp").forward(req, resp);
  }

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    long userId = (long) req.getSession().getAttribute("userId");
    long addUserID = Long.parseLong(req.getParameter("addUserID"));
    friendsService.approveFriendship(addUserID, userId);
    resp.sendRedirect("incoming_and_added_friend_requests");
  }
}

