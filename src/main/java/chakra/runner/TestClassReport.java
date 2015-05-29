package chakra.runner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestClassReport {
  private String testClassName;
  private List<TestResult> results;
}
