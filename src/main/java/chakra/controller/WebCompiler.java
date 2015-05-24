package chakra.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebCompiler {
  @RequestMapping("/compiler")
  public String getProblem(@RequestBody String request) {
    return "Compiled : " + request ;
  }
}
