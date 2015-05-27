package chakra.spec.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ApiSpec {
  private final Object response;
  private final Object request;
  private final String name;
  private final boolean skipped;

  public ApiSpec(Map contract) {
    this.request = contract.get("request");
    this.response = contract.get("response");
    this.skipped =  contract.get("skipped") != null;
    this.name = contract.get("name").toString();
  }

  public static List<ApiSpec> loadFrom(String jsonFilePath) throws IOException {
    return toSpecs((List<Map>) readFrom(jsonFilePath).get("contracts"));
  }

  private static List<ApiSpec> toSpecs(List<Map> contracts) {
    List<ApiSpec> specs = new ArrayList<ApiSpec>();
    for (Map contract : contracts) {
      specs.add(new ApiSpec(contract));
    }
    return specs;
  }

  private static Map readFrom(String resource) throws IOException {
    return new ObjectMapper().readValue(ApiSpec.class.getResourceAsStream(resource), Map.class);
  }
}
