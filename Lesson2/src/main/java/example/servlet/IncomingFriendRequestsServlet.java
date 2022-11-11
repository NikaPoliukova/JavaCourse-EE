/*package example.servlet;

import example.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    //если нажата кнопка add or cancel
    //удалить запись в таблице входящие заявки
    //if (add){
    //создать запись в таблице друзья
  }
}*/
