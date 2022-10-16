package example.servlet;

import example.model.User;
import example.service.UserService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String searchValue = req.getParameter("search");
        final List<User> users;
        if (searchValue == null) {
            users = userService.findUsers();
        } else {
           users= userService.findUserWithSearch(searchValue);
        }
        req.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/newusers.jsp").forward(req, resp);
    }
}
