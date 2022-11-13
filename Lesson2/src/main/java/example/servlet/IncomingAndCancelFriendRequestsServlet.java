package example.servlet;

import example.service.FriendsService;
import lombok.SneakyThrows;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/incoming_and_cancel_friend_requests")
public class IncomingAndCancelFriendRequestsServlet extends HttpServlet {

  private FriendsService friendsService;

  @SneakyThrows
  @Override
  public void init(ServletConfig config) {
    super.init(config);
    friendsService = (FriendsService) config.getServletContext().getAttribute("friendsService");
  }

  @SneakyThrows
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    long userId = (long) req.getSession().getAttribute("userId");
    long cancelUserID = Long.parseLong(req.getParameter("cancelUserID"));
    friendsService.cancelFriendship(cancelUserID, userId);
    resp.sendRedirect("incoming_and_added_friend_requests");
  }
}
