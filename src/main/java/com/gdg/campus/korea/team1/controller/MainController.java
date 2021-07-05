package com.gdg.campus.korea.team1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @RequestMapping("/")
  public String index() {

    return "Greetings from Spring Boot! - [GDG Team 1]";
  }

  @RequestMapping("/liveness_check")
  public String liveness_check() {
    return "OK";
  }
}
