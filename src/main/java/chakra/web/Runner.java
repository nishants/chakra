package chakra.web;

import chakra.web.request.ExecuteMainRequest;
import chakra.web.response.ExecuteMainResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping("/runner")
public class Runner {

  @RequestMapping(value = "/main", method = POST)
  public Data<ExecuteMainResponse> runMainMethod(@RequestBody Data<ExecuteMainRequest> request) throws Exception {
    ExecuteMainRequest content = request.getContent();

    Data<ExecuteMainResponse> respone = new Data<ExecuteMainResponse>();
    respone.setContent(new ExecuteMainResponse(new String[0], new String[0], "okay"));
    return respone;
  }
}
