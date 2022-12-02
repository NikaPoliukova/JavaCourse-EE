package example.controller;

import example.AuthContext;
import example.dto.UserDto;
import example.service.HashPassServiceImpl;
import example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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
@RequestMapping("/registration")
@AllArgsConstructor
public class RegisterController {
  private final UserService userService;
  private HashPassServiceImpl hashPassService;


  @GetMapping
  protected String userRegistration(Model model) {
    model.addAttribute("dto", new UserDto());
    return "registration";
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  protected RedirectView userRegistration(@Valid @ModelAttribute("dto") UserDto dto) {
    userService.addUser(dto.getUserName(), hashPassService.hashPass(dto.getPassword()));
       return new RedirectView("/login");
  }
}
