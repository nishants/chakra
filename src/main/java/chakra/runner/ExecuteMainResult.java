package chakra.runner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecuteMainResult {
  private String[] console;
  private String[] error;
  private String result;
}
