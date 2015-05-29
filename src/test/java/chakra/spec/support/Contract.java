package chakra.spec.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class Contract {
  private final Map response;
  private final Object request;
  private final String name;
  private final boolean skipped;

  public Contract(Map contract) {
    this.request = contract.get("request");
    this.response = (Map)contract.get("response");
    this.skipped =  contract.get("skipped") != null;
    this.name = contract.get("name").toString();
  }

  public static List<Contract> loadFrom(String jsonFilePath) throws IOException {
    return toSpecs((List<Map>) readFrom(jsonFilePath).get("contracts"));
  }

  private static List<Contract> toSpecs(List<Map> contracts) {
    List<Contract> specs = new ArrayList<Contract>();
    for (Map contract : contracts) {
      specs.add(new Contract(contract));
    }
    return specs;
  }

  private static Map readFrom(String resource) throws IOException {
    return new ObjectMapper().readValue(Contract.class.getResourceAsStream(resource), Map.class);
  }
}
