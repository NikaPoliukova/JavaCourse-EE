/*package example.servlet;

import example.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/outgoing_friend_requests")
public class OutgoingFriendRequestsServlet extends HttpServlet {
  //исходщие заявки в друзья
  private UserService userService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    userService = (UserService) config.getServletContext().getAttribute("userService");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doGet(req, resp);

    //получить запрос с сервлета usersServlet

    //удалить из таблицы исходящие заявки
    //if (isNotFound){
    //добавить в таблицу friends


  }
}*/
