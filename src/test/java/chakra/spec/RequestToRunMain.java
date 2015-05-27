package chakra.spec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

@AllArgsConstructor
public class RequestToRunMain {
  @Setter
  @Getter
  private Map content;

  public static RequestToRunMain create(String mainClassFile, String...javaFiles){
    Map content = new HashMap();
    content.put("mainClass", mainClassFile);
    content.put("classes", asList(javaFiles));
    return new RequestToRunMain(content);
  }
}
