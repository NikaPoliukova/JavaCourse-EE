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

@WebServlet("/incoming_friend_requests")
public class IncomingFriendRequestsServlet extends HttpServlet {
  //входщие заявки в друзья
  private UserService userService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    userService = (UserService) config.getServletContext().getAttribute("userService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<User> incomingFriendRequests;
    long userId = (long) req.getSession().getAttribute("userId");
    incomingFriendRequests = userService.findIncomingFriendRequests(userId);
    req.setAttribute("incomingFriendRequests", incomingFriendRequests);
    getServletContext().getRequestDispatcher("/incoming_friend_requests.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    long userId = (long) req.getSession().getAttribute("userId");
    if (req.getParameter("addUserID") != null) {
      long addUserID = Long.parseLong(req.getParameter("addUserID"));
      userService.approveFriendship(addUserID, userId);
    } else if (req.getParameter("cancelUserID") != null) {
      long cancelUserID = Long.parseLong(req.getParameter("cancelUserID"));
      userService.cancelFriendship(cancelUserID, userId);
    }
    resp.sendRedirect("incoming_friend_requests");
  }
}

