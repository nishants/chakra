package chakra.web;

import chakra.web.request.Request;
import chakra.web.request.RunnableMain;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping("/runner")
public class Runner {

  @RequestMapping(value = "/main", method = POST)
  public Request runMainMethod(@RequestBody Request<RunnableMain> request) throws Exception {
    Request<String> response = new Request<String>();
    RunnableMain content = request.getContent();
    response.setContent(content.getMainClass());
    return response ;
  }
}
