package chakra.runner;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExecuteTestResult {
  private List<TestClassReport> tests = new ArrayList<TestClassReport>();
  private String[] error = {} ;
  private String result = "okay";

}
