package example.controller;

import example.dto.UserDto;
import example.service.HashPassService;
import example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;


@Validated
@Controller
@AllArgsConstructor
public class RegisterController {
  private final UserService userService;
  private HashPassService hashPassService;

  @GetMapping(path = "/registration")
  protected String userRegistration(Model model) {
    System.out.println("зашли в регистрация контроллер метод гет");
    model.addAttribute("dto", new UserDto());
    return "registration";
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  protected RedirectView userRegistration(@Valid @ModelAttribute("dto") UserDto dto) {
    userService.addUser(dto.getUserName(), hashPassService.hashPass(dto.getPassword()));
    return new RedirectView("/login");
  }
}
