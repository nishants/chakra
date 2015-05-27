package chakra.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecuteMainResponse {
  private String[] console;
  private String[] error;
  private String result;
}
