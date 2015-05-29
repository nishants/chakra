package chakra.runner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {
  private String name;
  private String result;
  private String message = "";
}
