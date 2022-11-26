package example.controller;


import example.AuthContext;
import example.model.User;
import example.service.HashPassService;
import example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Validated
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;
  private final AuthContext authContext;
  private HashPassService hashPassService;

  @GetMapping
  protected String authorizationUser() {
    return "login";
  }

  @PostMapping
  protected RedirectView userAuthorization(Model model, @RequestParam("username") String username,
                                           @RequestParam("password") String password) {
    String hashPass = userService.getUserPassword(username);
    if (hashPassService.verify(password, hashPass)) {
      User user = userService.getUser(username, hashPass);
      model.addAttribute("username", username);
      model.addAttribute("userId", user.getUserId());
      authContext.setAuthorized(true);
      return new RedirectView("/users");
    } else {
      return new RedirectView("login?error=" + " enter incorrect password");
    }
  }
}
