package com.example.users.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@ControllerAdvice
public class ExceptionMapper {
  @ExceptionHandler(Exception.class)
  public ModelAndView handleException(final Exception exception) {
    ModelAndView model = new ModelAndView();
    model.addObject("message", exception.getMessage());
    log.error(exception.getMessage());
    model.setViewName("error");
    return model;
  }
}


