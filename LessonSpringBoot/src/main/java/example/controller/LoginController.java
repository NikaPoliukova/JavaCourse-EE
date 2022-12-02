package example.controller;


import example.AuthContext;
import example.dto.UserDto;
import example.model.User;
import example.service.HashPassServiceImpl;
import example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Validated
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;
  private final AuthContext authContext;
  private final HashPassServiceImpl hashPassService;

  @GetMapping
  protected String userLogin(Model model) {
    model.addAttribute("dto", new UserDto());
     return "login";
  }

  @PostMapping
  protected RedirectView userAuthorization(Model model, @Valid @ModelAttribute("dto") UserDto dto) {
    String hashPass = userService.getUserPassword(dto.getUserName());
    if (hashPassService.verify(dto.getPassword(), hashPass)) {
      userService.getUser(dto.getUserName(), hashPass);
      model.addAttribute("userName", dto.getUserName());
      User user= userService.getUser(dto.getUserName(), hashPass);
      Long userId = user.getUserId();
      model.addAttribute("userId", userId);
      authContext.setUserId(user.getUserId());
      authContext.setUserName(user.getUserName());
      authContext.setAuthorized(true);
      return new RedirectView("/users");
    } else {
      return new RedirectView("login?error=" + " enter incorrect password");
    }
  }
}
