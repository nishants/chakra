package org.geeksaints.chakra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
  @RequestMapping("/")
  public String login() {
    return "hi ! i am chakra-compiler-server" ;

  }
}
