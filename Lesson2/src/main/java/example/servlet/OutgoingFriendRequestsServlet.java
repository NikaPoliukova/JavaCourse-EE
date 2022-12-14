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

@WebServlet("/outgoing_friend_requests")
public class OutgoingFriendRequestsServlet extends HttpServlet {

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
    List<User> outgoingFriendRequest;
    long userId = (long) req.getSession().getAttribute("userId");
    outgoingFriendRequest = friendsService.findOutgoingFriendsRequests(userId);
    req.setAttribute("outgoingFriendRequest", outgoingFriendRequest);
    getServletContext().getRequestDispatcher("/outgoing_friend_requests.jsp").forward(req, resp);
  }

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    long userId = (long) req.getSession().getAttribute("userId");
    if (req.getParameter("cancelUserID") != null) {
      long cancelUserID = Long.parseLong(req.getParameter("cancelUserID"));
      friendsService.cancelFriendship(userId, cancelUserID);
      resp.sendRedirect("outgoing_friend_requests");
    }
  }
}

