package chakra.spec.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Response {
  public static List<String> popMessagesFrom(Map body) {
    List<String> messages = new ArrayList<String>();
    for(Object test: list(map(body.get("content")).get("tests"))){
      for(Object result: list(map(test).get("results"))){
        messages.add(map(result).remove("message").toString());
      }
    }
    return messages;
  }

  private static Map map(Object map){
    return (Map)map;
  }

  private static List list(Object map){
    return (List)map;
  }
}
