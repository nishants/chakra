package chakra.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProblemsController {
  @RequestMapping("/problems/{id}")
  public String getProblem(@PathVariable long id) {
    return "hi ! i am chakra-compiler-server" ;
  }
}
