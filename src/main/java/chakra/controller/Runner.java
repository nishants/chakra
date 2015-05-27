package chakra.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping("/runner")
public class Runner {

  @RequestMapping(value = "/main", method = POST)
  public String runMainMethod(@RequestBody String request) throws Exception {

    return request ;
  }
}
