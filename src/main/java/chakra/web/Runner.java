package chakra.web;

import chakra.web.request.Request;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping("/runner")
public class Runner {

  @RequestMapping(value = "/main", method = POST)
  public Request runMainMethod(@RequestBody Request<Map> request) throws Exception {
    Request<String> response = new Request<String>();

    response.setContent(request.getContent().get("mainClassName").toString());
    return response ;
  }
}
